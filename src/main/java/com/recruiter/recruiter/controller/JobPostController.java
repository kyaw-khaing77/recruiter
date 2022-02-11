package com.recruiter.recruiter.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.stream.Collectors;
import java.util.concurrent.TimeUnit;

import com.recruiter.recruiter.domain.Company;
import com.recruiter.recruiter.domain.JobPost;
import com.recruiter.recruiter.domain.Payment;
import com.recruiter.recruiter.service.CompanyService;
import com.recruiter.recruiter.service.JobPostService;
import com.recruiter.recruiter.service.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.recruiter.recruiter.domain.Company;
import com.recruiter.recruiter.domain.JobPost;
import com.recruiter.recruiter.domain.Payment;
import com.recruiter.recruiter.domain.User;
import com.recruiter.recruiter.service.CompanyService;
import com.recruiter.recruiter.service.JobPostService;
import com.recruiter.recruiter.service.PaymentService;
import com.recruiter.recruiter.service.UserService;

@Controller
public class JobPostController {
	
    @Autowired
    JobPostService postService;
    
    @Autowired
    UserService userService;

    @Autowired
    CompanyService companyService;

    @Autowired
    PaymentService paymentService;

    static List<String> jobLocationsList = new ArrayList<String>(){{
                                        add("Yangon");
                                        add("Mandalay");
                                        add("Nay Pyi Thaw");
                                        add("Mon");
                                        add("Shan");
                                        add("Bago");
                                        add("Sagaing");
                                        add("Kachin");
                                        add("Ayeyarwady");
                                        add("Rakhine");
                                        add("Magway");
                                        add("Tanintharyi");
                                        add("Kayin");
                                        add("Kaya");
    }};

   static List<String> jobCategoriesList = new ArrayList<String>(){{
                                        add("Administration, business and management");
                                        add("Alternative therapies");
                                        add("Animals, land and environment");
                                        add("Computing and ICT");
                                        add("Construction and building");
                                        add("Design, arts and crafts");
                                        add("Education and training");
                                        add("Engineering");
                                        add("Facilities and property services");
                                        add("Financial services");
                                        add("Garage services");
                                        add("Hairdressing and beauty");
                                        add("Healthcare");
                                        add("Heritage, culture and libraries");
                                        add("Hospitality, catering and tourism");
                                        add("Languages");
                                        add("Legal and court services");
                                        add("Manufacturing and production");
                                        add("Performing arts and media");
                                        add("Print and publishing, marketing and advertising");
                                        add("Retail and customer services");
                                        add("Science, mathematics and statistics");
                                        add("Security, uniformed and protective services");
                                        add("Social sciences and religion");
                                        add("Social work and caring services");
                                        add("Sport and leisure");
                                        add("Transport, distribution and logistics");
    }};
        
    @RequestMapping("/postJob")
    private String postJob(Model model) {

        JobPost post = new JobPost();

        model.addAttribute("jobLocationsList", jobLocationsList);
        model.addAttribute("jobCategoriesList", jobCategoriesList);

        model.addAttribute("post", post);

        return "post_job";
    }
    
    @PostMapping("/addPost")
    private String addPost(Principal principal,@ModelAttribute("post") JobPost post, Model model) {
    	
        User user = userService.findByUsername(principal.getName());
        Company company = companyService.findByUser(user);

        Payment payment = paymentService.findByCompany(company);

        if(payment == null){
            payment = new Payment();
            model.addAttribute("payment", payment);
            return "company_payment";
        }
        companyService.updateJobPost(post, company, payment);
        
        return "redirect:/viewPosts";
    }
   

    @RequestMapping("/viewPosts")
    private String viewPosts(@RequestParam(defaultValue = "1") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize, Model model, Principal principal){

        // Company company = companyService.findByCompanyName("gid");
        User user = userService.findByUsername(principal.getName());
        Company company = companyService.findByUser_Id(user.getUserId());
        Page<JobPost> jobPost = new PageImpl<>(company.getPost(), PageRequest.of(pageNo-1, pageSize), company.getPost().size());
        model.addAttribute("listOfPosts", jobPost.getContent());
        model.addAttribute("noOfPosts", jobPost.getContent().size());
        model.addAttribute("totalPages", jobPost.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("hasNext", jobPost.hasNext());
        model.addAttribute("hasPrevious", jobPost.hasPrevious());
        model.addAttribute("url", "viewPosts");
        model.addAttribute("jobCategoriesList", jobCategoriesList);
        model.addAttribute("jobLocationsList", jobLocationsList);
        return "uploaded-post";
    }
    
    @PostMapping("/jobPostFilter")
    private String jobPostFilter(@ModelAttribute("post") JobPost filterPost,
    		@ModelAttribute("jobTypeFullTime") String jobTypeFullTime,
    		@ModelAttribute("jobTypePartTime") String jobTypePartTime,
    		@ModelAttribute("jobTypeFreelancer") String jobTypeFreelancer,
    		@ModelAttribute("jobTypeIntership") String jobTypeIntership,
    		Model model, Principal principal){
    	
		/*
		 * User user = userService.findByUsername(principal.getName()); Company company
		 * = companyService.findByUser(user);
		 */
          List<JobPost> jobList = postService.findAllByStatus(true);
    	
    	  List<JobPost> filteredPostList = jobList;
   
		  
		  if(filterPost.getJobTitle() != null && (!filterPost.getJobTitle().equals(""))) {
			  filteredPostList = filteredPostList.stream(). filter(jobPostList ->
			  jobPostList.getJobTitle().contains(filterPost.getJobTitle())).collect(Collectors.toList());
		  }
		  
		  if(filterPost.getJobCategory() != null && (!filterPost.getJobCategory().equals("")) && (!filterPost.getJobCategory().equals("allJobCategory"))) {
			  filteredPostList = filteredPostList.stream(). filter(jobPostList ->
			  jobPostList.getJobCategory().equals(filterPost.getJobCategory())).collect(Collectors.toList());
		  }
		  
		  if(filterPost.getJobLevel() != null && (!filterPost.getJobLevel().equals("")) && (!filterPost.getJobLevel().equals("allJobLevel"))) {
			  filteredPostList = filteredPostList.stream(). filter(jobPostList ->
			  jobPostList.getJobLevel().equals(filterPost.getJobLevel())).collect(Collectors.toList());
		  }
		  
		  if(jobTypeFullTime != null && (!jobTypeFullTime.equals(""))) {
			  filteredPostList = filteredPostList.stream(). filter(jobPostList ->
			  jobPostList.getJobType().equals(jobTypeFullTime)).collect(Collectors.toList());
		  }
		  
		  if(jobTypePartTime != null && (!jobTypePartTime.equals(""))) {
			  filteredPostList = filteredPostList.stream(). filter(jobPostList ->
			  jobPostList.getJobType().equals(jobTypePartTime)).collect(Collectors.toList());
		  }
		  
		  if(jobTypeIntership != null && (!jobTypeIntership.equals(""))) {
			  filteredPostList = filteredPostList.stream(). filter(jobPostList ->
			  jobPostList.getJobType().equals(jobTypeIntership)).collect(Collectors.toList());
		  }
		  
		  if(jobTypeFreelancer != null && (!jobTypeFreelancer.equals(""))) {
			  filteredPostList = filteredPostList.stream(). filter(jobPostList ->
			  jobPostList.getJobType().equals(jobTypeFreelancer)).collect(Collectors.toList());
		  }
		  
		  if(filterPost.getJobLocation() != null && (!filterPost.getJobLocation().equals("")) && (!filterPost.getJobLocation().equals("allLocation"))) {
			  filteredPostList = filteredPostList.stream(). filter(jobPostList ->
			  jobPostList.getJobLocation().equals(filterPost.getJobLocation())).collect(Collectors.toList());
		  }
		  
		  if(filterPost.getMinSalary() != null && filterPost.getMinSalary() != 0) {
			  filteredPostList = filteredPostList.stream(). filter(jobPostList ->
			  jobPostList.getMinSalary() >= filterPost.getMinSalary() ).collect(Collectors.toList());
		  }
    	
    	
    	JobPost post = new JobPost();
        // Company company = companyService.findByCompanyName(principal.getName());
        model.addAttribute("jobLocationsList", jobLocationsList);
        model.addAttribute("jobCategoriesList", jobCategoriesList);
        model.addAttribute("listOfPosts", filteredPostList);
        model.addAttribute("noOfPosts", filteredPostList.size());
        model.addAttribute("post", post);
        return "uploaded-post";

    }
    
    @PostMapping("/jobPostFilterByDate")
    private String jobPostFilterByDate(@ModelAttribute(value = "searchPostsWithDate") String searchPostsWithDate,
    		Model model, Principal principal){
    	
    	User user = userService.findByUsername(principal.getName());
        Company company = companyService.findByUser(user);
    	
         List<JobPost> jobList = company.getPost();
         
         List<JobPost> filteredPostList = jobList;
         
         Integer day = Integer.parseInt(searchPostsWithDate);
         
         LocalDate today = LocalDate.now();  
         LocalDate searchWithDate = today.minusDays(day);
         
         Date date = Date.from(searchWithDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    	   
         if(searchPostsWithDate != null && (!searchPostsWithDate.equals(""))) {
			  filteredPostList = filteredPostList.stream(). filter(jobPostList ->
			  jobPostList.getCreatedAt().after(date) ).collect(Collectors.toList());
			  System.out.println(filteredPostList);
			  System.out.println("Before loop");
		  }
    	
    	JobPost post = new JobPost();
        // Company company = companyService.findByCompanyName(principal.getName());
        model.addAttribute("jobLocationsList", jobLocationsList);
        model.addAttribute("jobCategoriesList", jobCategoriesList);
        model.addAttribute("listOfPosts", filteredPostList );
        model.addAttribute("noOfPosts", filteredPostList.size());
        model.addAttribute("post", post);
        return "uploaded-post";

    }
   

    @RequestMapping("/updatePost/{postId}")
    private String updatePost(@PathVariable("postId") Long postId, Model model){
        JobPost post = postService.findById(postId);

        model.addAttribute("jobLocationsList", jobLocationsList);
        model.addAttribute("jobCategoriesList", jobCategoriesList);

        model.addAttribute("post", post);
        return "post_job";
    }

    @RequestMapping("/deletePost/{postId}")
    private String deletePost(@PathVariable("postId") Long postId){
        postService.removeById(postId);
        return "redirect:/viewPosts";
    }

    @RequestMapping("/jobPostDetails/{jobPostId}")
    private String jobPostDetails(@PathVariable("jobPostId") Long jobPostId, Model model){
        JobPost jobPost = postService.findById(jobPostId);
        
        Long createdAt = jobPost.getCreatedAt().getTime();
        
        Date currentDate = new Date();
        Long currentTime = currentDate.getTime();

        long diffInMillies = Math.abs(currentTime - createdAt);
        long dateDiff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        model.addAttribute("dateDiff", dateDiff);
        model.addAttribute("jobPost", jobPost);

        return "job-post-detail";
    }
}
