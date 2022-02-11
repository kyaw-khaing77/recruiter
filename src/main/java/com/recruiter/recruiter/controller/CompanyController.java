package com.recruiter.recruiter.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

import com.recruiter.recruiter.domain.Company;
import com.recruiter.recruiter.domain.Payment;
import com.recruiter.recruiter.domain.User;
import com.recruiter.recruiter.service.CompanyService;
import com.recruiter.recruiter.service.UserService;
import com.recruiter.recruiter.service.impl.UserSecurityService;
import com.recruiter.recruiter.utility.SecurityUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class CompanyController {
   
    @Autowired
    private CompanyService companyService;

    @Autowired
	private UserSecurityService userSecurityService;
  
  
    @Autowired
    UserService userService;
     
    @RequestMapping("/newCompany")
    private String createCompanyProfile(Model model, Principal principal){
        // User user = userService.findByUsername(principal.getName());
        User user = userService.findByUsername("gid");
        Company company=new Company();
        model.addAttribute("company", company);
        model.addAttribute("user", user);
        model.addAttribute("action", "create");
        return "company_edit";
    }

    @RequestMapping("/companyAccount")
    private String companyAccount(Model model, Principal principal){
        // User user = userService.findByUsername(principal.getName());
        User user = userService.findByUsername("gid");
        Company company = companyService.findByUser_Id(user.getUserId());
        model.addAttribute("company", company);
        model.addAttribute("user", user);
        model.addAttribute("action", "update");
        return "company_edit";
    }
    
    @PostMapping(value="/newCompany") 
    public String newCompany(@ModelAttribute("company") Company company,
    @ModelAttribute("password") String password,@ModelAttribute("user") User nowUser,
    @ModelAttribute("newPassword") String newPassword, @ModelAttribute("companyLogo") MultipartFile companyLogo,
    @ModelAttribute("companyFeaturePhotos") List<MultipartFile> companyFeaturePhotos,Model model, Principal principal ) throws Exception { 
        
        byte[] bytes = companyLogo.getBytes();
        
        String name = "logo"+ company.getCompanyName() + ".png";
        
        BufferedOutputStream stream = new BufferedOutputStream(
        new FileOutputStream(new File("src/main/resources/static/image/companyLogo/" + name)));
        
        stream.write(bytes);
        
        int i = 0;
        for(MultipartFile companyFeaturePhoto : companyFeaturePhotos){
            String fileName = "featurePhoto"+ company.getCompanyName() + i + ".png";
            byte[] bytes1 = companyFeaturePhoto.getBytes();
            stream = new BufferedOutputStream(
                new FileOutputStream(new File("src/main/resources/static/image/companyFeaturePhoto/" + fileName)));
            i++;
            stream.write(bytes1);
        }
        stream.close();
        
    // company.setUser(nowUser);
    if(nowUser == null) { 
        throw new Exception ("User not found"); 
      }

      User currentUser = userService.findByUsername(principal.getName());
    
     //check email already exists 
      if (userService.findByEmail(nowUser.getEmail()) !=null) {
          if(userService.findByEmail(nowUser.getEmail()).getUserId() != currentUser.getUserId()) {
              model.addAttribute("emailExists", true);
              return "company_edit";
          }
      }
      
      /*check username already exists*/
      if (userService.findByUsername(nowUser.getUsername())!=null){
          if(userService.findByUsername(nowUser.getUsername()).getUserId() != nowUser.getUserId()) {
              model.addAttribute("usernameExists", true);
              return "company_edit";
          }
      }
    
      //update password
      if (newPassword != null && !newPassword.isEmpty() && !newPassword.equals("")){
          BCryptPasswordEncoder passwordEncoder = SecurityUtility.passwordEncoder();
          String dbPassword = currentUser.getPassword();
          if(passwordEncoder.matches(password, dbPassword)){
  
            currentUser.setPassword(passwordEncoder.encode(newPassword));
          } else {
              model.addAttribute("incorrectPassword", true);
              
              return "company_edit";
          }
      }
    
   
   
      Company currentCompany = companyService.findByUser_Id(currentUser.getUserId());
      currentCompany.setCompanyName(company.getCompanyName());
      currentCompany.setCompanyPhone(company.getCompanyPhone());
      currentCompany.setCompanyWebsite(company.getCompanyWebsite());
      currentCompany.setCompanyType(company.getCompanyType());
      currentCompany.setNoOfEmployee(company.getNoOfEmployee());
      currentCompany.setCompanyAddress(company.getCompanyAddress());
      currentCompany.setCompanyMission(company.getCompanyMission());
      currentCompany.setCompanyVision(company.getCompanyVision());
      // user.setPassword(SecurityUtility.passwordEncoder().encode(password));
        
    //   currentCompany.setUser(nowUser);  
  //  userService.save(currentUser);
    companyService.save(currentUser, currentCompany);
    
    model.addAttribute("updateSuccess", true);
    model.addAttribute("classActiveEdit", true);
    
    
    UserDetails userDetails = userSecurityService.loadUserByUsername(nowUser.getUsername());
    
    Authentication authentication = new
    UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
    userDetails.getAuthorities());
    
    SecurityContextHolder.getContext().setAuthentication(authentication);
    
    return "redirect:/";
    
   }

    
}
