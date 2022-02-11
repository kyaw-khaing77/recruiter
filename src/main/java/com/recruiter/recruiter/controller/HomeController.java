package com.recruiter.recruiter.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.recruiter.recruiter.domain.JobPost;
import com.recruiter.recruiter.service.JobPostService;

@Controller
public class HomeController {
	
    @Autowired
    JobPostService jobPostService;

    @RequestMapping( {"/", "/index"} )
    private String index(Model model){
        List<JobPost> jobPosts = jobPostService.findFirst5ByStatusOrderByUpdatedAt(true);

        
        Map<String, Long> counting = jobPostService.findAllByStatus(true).stream().collect(
                Collectors.groupingBy(JobPost::getJobCategory, Collectors.counting()));

	   Map<String, Long> finalMapDescendingOrder = new LinkedHashMap<>();
	   Map<String, String> mostAppliedJobLists = new LinkedHashMap<>();
	   
	      
	// Sort a map and add to finalMap
	  counting.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed())
			.forEachOrdered(e -> finalMapDescendingOrder.put(e.getKey(), e.getValue())) ;
	  
	  //Maximun 8 
	  finalMapDescendingOrder.entrySet().stream().limit(8)
		.forEachOrdered(e -> mostAppliedJobLists.put(e.getKey(), e.getValue()+" Job Posts")) ;
        
        model.addAttribute("jobLocationsList", JobPostController.jobLocationsList);
        model.addAttribute("jobCategoriesList", JobPostController.jobCategoriesList);
        
        model.addAttribute("listOfPosts", jobPosts);
        model.addAttribute("mostAppliedJobLists", mostAppliedJobLists);
        return "index";
    }
    
    
    
    @RequestMapping("/getPostsByCategory/{categoryName}")
    private String getPostsByCategory(@PathVariable("categoryName") String categoryName,Model model){
        List<JobPost> jobPosts = jobPostService.findByJobCategoryAndStatus(categoryName, true);
        
        
      	JobPost post = new JobPost();
      	post.setJobCategory(categoryName);
        // Company company = companyService.findByCompanyName(principal.getName());
        model.addAttribute("jobLocationsList", JobPostController.jobLocationsList);
        model.addAttribute("jobCategoriesList", JobPostController.jobCategoriesList);
        model.addAttribute("listOfPosts", jobPosts);
        model.addAttribute("noOfPosts", jobPosts.size());
        model.addAttribute("post", post);
        return "uploaded-post";
    }
    
    @RequestMapping("/getPostCountByCategory")
    private String getPossCountByCategory(Model model){
        
    	  Map<String, Long> counting = jobPostService.findAllByStatus(true).stream().collect(
                  Collectors.groupingBy(JobPost::getJobCategory, Collectors.counting()));

  	   Map<String, Long> finalMapDescendingOrder = new LinkedHashMap<>();
  	   Map<String, String> jobListCountByCategory = new LinkedHashMap<>();
  	   
  	      
  	// Sort a map and add to finalMap
  	  counting.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed())
  			.forEachOrdered(e -> finalMapDescendingOrder.put(e.getKey(), e.getValue())) ;
  	  
  	  //Maximun 8 
  	    finalMapDescendingOrder.entrySet().stream()
  		.forEachOrdered(e -> jobListCountByCategory.put(e.getKey(), e.getValue()+" Job Posts")) ;
          
          model.addAttribute("jobListCountByCategory", jobListCountByCategory);
          
          System.out.println(jobListCountByCategory);
  
        return "category_postCounts";
    }
    
    @RequestMapping("/searchPostWithCategoryAndLocation")
    private String searchPostWithCategoryAndLocation(Model model,
    		@ModelAttribute("jobCategory") String jobCategory,
    		@ModelAttribute("jobLocation") String jobLocation,
    		@ModelAttribute("jobName") String jobName){
       
    	List<JobPost> jobList = jobPostService.findAllByStatus(true);
    	
       List<JobPost> filteredPostList = jobList;
  
         if(jobName != null && (!jobName.equals(""))) {
			  filteredPostList = filteredPostList.stream(). filter(jobPostList ->
			  jobPostList.getJobTitle().contains(jobName)).collect(Collectors.toList());
		  }
		  
		  if(jobCategory != null && (!jobCategory.equals("")) && (!jobCategory.equals("allJobCategory"))) {
			  filteredPostList = filteredPostList.stream(). filter(jobPostList ->
			  jobPostList.getJobCategory().equals(jobCategory)).collect(Collectors.toList());
		  }
		  
		  if(jobLocation != null && (!jobLocation.equals("")) && (!jobLocation.equals("allLocation"))) {
			  filteredPostList = filteredPostList.stream(). filter(jobPostList ->
			  jobPostList.getJobLocation().equals(jobLocation)).collect(Collectors.toList());
		  }
    	
		   JobPost post = new JobPost();
	        // Company company = companyService.findByCompanyName(principal.getName());
		    model.addAttribute("jobLocationsList", JobPostController.jobLocationsList);
	        model.addAttribute("jobCategoriesList", JobPostController.jobCategoriesList);
	        model.addAttribute("listOfPosts", filteredPostList);
	        model.addAttribute("noOfPosts", filteredPostList.size());
	        model.addAttribute("post", post);
	        return "uploaded-post";
    }

    @RequestMapping("/allJobs")
    private String allJobs(@RequestParam(defaultValue = "1") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize, Model model){
        Page<JobPost> jobPosts = jobPostService.findAllByStatusOrderByUpdatedAt(pageNo-1, pageSize, true);
        model.addAttribute("listOfPosts", jobPosts.getContent());
        model.addAttribute("noOfPosts", jobPosts.getContent().size());
        model.addAttribute("totalPages", jobPosts.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("hasNext", jobPosts.hasNext());
        model.addAttribute("hasPrevious", jobPosts.hasPrevious());
        model.addAttribute("url", "allJobs");
        return "uploaded-post";
    }
    
    @RequestMapping("/about")
    private String about(Model model)
    {
    	return "about_us.html";
    }
}
