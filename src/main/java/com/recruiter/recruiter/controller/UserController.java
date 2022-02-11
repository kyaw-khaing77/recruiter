package com.recruiter.recruiter.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.recruiter.recruiter.domain.Company;
import com.recruiter.recruiter.domain.EndUser;
import com.recruiter.recruiter.domain.User;
import com.recruiter.recruiter.domain.security.PasswordResetToken;
import com.recruiter.recruiter.domain.security.Role;
import com.recruiter.recruiter.domain.security.UserRole;
import com.recruiter.recruiter.service.CompanyService;
import com.recruiter.recruiter.service.EndUserService;
import com.recruiter.recruiter.service.UserService;
import com.recruiter.recruiter.service.impl.UserSecurityService;
import com.recruiter.recruiter.utility.MailConstructor;
import com.recruiter.recruiter.utility.SecurityUtility;

@Controller
public class UserController {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private MailConstructor mailConstructor;

	@Autowired
	private UserService userService;

	@Autowired
	private EndUserService endUserService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private UserSecurityService userSecurityService;

	@RequestMapping("/login")
	public String login(Model model) {
		return "user_login";
	}

	@RequestMapping("/userRegister")
	public String newUserForm(Model model) {
		return "user_register";
	}

	@RequestMapping(value = "/newUser", method = RequestMethod.POST)
	public String newUserPost(HttpServletRequest request, @ModelAttribute("email") String userEmail,
			@ModelAttribute("username") String username, @ModelAttribute("selectRole") String selectRole, Model model)
			throws Exception {
		model.addAttribute("email", userEmail);
		model.addAttribute("username", username);

		if (userService.findByUsername(username) != null) {
			model.addAttribute("usernameExists", true);

			return "user_register";
		}

		if (userService.findByEmail(userEmail) != null) {
			model.addAttribute("emailExists", true);

			return "user_register";
		}

		User user = new User();
		user.setUsername(username);
		user.setEmail(userEmail);

		String password = SecurityUtility.randomPassword();

		String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
		user.setPassword(encryptedPassword);

		Role role = new Role();
		if (selectRole.equals("End User")) {
			role.setRoleId(1l);
			role.setName("ROLE_END_USER");

		} else if (selectRole.equals("Company")) {
			role.setRoleId(2l);
			role.setName("ROLE_COMPANY");
			
		}

		Set<UserRole> userRoles = new HashSet<>();
		userRoles.add(new UserRole(user, role));
		userService.createUser(user, userRoles);
		
		

		String token = UUID.randomUUID().toString();
		userService.createPasswordResetTokenForUser(user, token);

		String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

		SimpleMailMessage email = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user,
				password);

		mailSender.send(email);

		model.addAttribute("emailSent", true);

		return "user_register";
	}

	@RequestMapping("/newUser")
	public String newUser(Locale locale, @RequestParam("token") String token, Model model) {
		PasswordResetToken passToken = userService.getPasswordResetToken(token);

		if (passToken == null) {
			String message = "Invalid Token.";
			model.addAttribute("message", message);
			return "redirect:/badRequest";
		}

		User user = passToken.getUser();
		String username = user.getUsername();
		UserDetails userDetails = userSecurityService.loadUserByUsername(username);

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
				userDetails.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		for (UserRole ur : user.getUserRoles()) {
			if (ur.getRole().getName().equals("ROLE_END_USER")) {
				EndUser endUser = endUserService.findByUser(user);
				model.addAttribute("endUser", endUser);
				model.addAttribute("username", user.getUsername());
				model.addAttribute("email", user.getEmail());
				model.addAttribute("userId", user.getUserId());
				return "endUser_edit";
			}

			if (ur.getRole().getName().equals("ROLE_COMPANY")) {
				Company company = companyService.findByUser(user);
				model.addAttribute("company", company);
				// model.addAttribute("username", user.getUsername());
				// model.addAttribute("email", user.getEmail());
				// model.addAttribute("userId", user.getUserId());
				model.addAttribute("user", user);
				return "company_edit";
			}
		}
		return "redirect:/login";

	}
 
	  @RequestMapping(value="/updateEndUserInfo", method=RequestMethod.POST) 
	  public String updateUserInfo(@ModelAttribute("endUser") EndUser endUser,
	  @ModelAttribute("username") String username,@ModelAttribute("email") String email,
	  @ModelAttribute("userId")Long userId,@ModelAttribute("password") String password,
	  @ModelAttribute("newPassword") String newPassword, Model model ) throws Exception { 
		  
      User currentUser = userService.findById(userId);
	  endUser.setUser(currentUser);
	  if(currentUser == null) { 
		  throw new Exception ("User not found"); 
		}
	  
	   //check email already exists 
		if (userService.findByEmail(email) !=null) {
			if(userService.findByEmail(email).getUserId() != currentUser.getUserId()) {
				model.addAttribute("emailExists", true);
				return "endUser_edit";
			}
		}
		
		/*check username already exists*/
		if (userService.findByUsername(username)!=null){
			if(userService.findByUsername(username).getUserId() != currentUser.getUserId()) {
				model.addAttribute("usernameExists", true);
				return "endUser_edit";
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
				
				return "endUser_edit";
			}
		}
	  
	  if(endUser.getUserPicture() != null || !endUser.getUserPicture().isEmpty()) {
	  
		  byte[] bytes = endUser.getUserPicture().getBytes();
		  
		  String name = currentUser.getUsername() + ".png";
		  
		  File tempFile = new File("src/main/resources/static/image/book/"+name);
		  
		  if(tempFile.exists()) {
			  Files.delete(Paths.get("src/main/resources/static/image/book/"+name));
		  }
		  
		  
		  BufferedOutputStream stream = new BufferedOutputStream( new
		  FileOutputStream(new File("src/main/resources/static/images/userImgs/" +
		  name)));
		  
		  stream.write(bytes); 
		  stream.close();
		  
	  }
	 
	  EndUser currentEndUser = endUserService.findByUser(currentUser);
	  currentEndUser.setFirstName(endUser.getFirstName());
	  currentEndUser.setLastName(endUser.getLastName());
	  currentEndUser.setAddress(endUser.getAddress());
	  currentEndUser.setAge(endUser.getAge());
	  currentEndUser.setBio(endUser.getBio());
	  currentEndUser.setEducation(endUser.getEducation());
	  currentEndUser.setGender(endUser.getGender());
	  currentEndUser.setLinkedIn(endUser.getLinkedIn());
	  currentEndUser.setPhone(endUser.getPhone());
	  currentEndUser.setWebsiteUrl(endUser.getWebsiteUrl());
			 
      currentUser.setUsername(username);
	  currentUser.setEmail(email);
	   currentEndUser.setUser(currentUser);  
	//  userService.save(currentUser);
	  endUserService.save(endUser);
	  
	  model.addAttribute("updateSuccess", true);
	  model.addAttribute("classActiveEdit", true);
	  
	  
	  UserDetails userDetails = userSecurityService.loadUserByUsername(currentUser.getUsername());
	  
	  Authentication authentication = new
	  UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
	  userDetails.getAuthorities());
	  
	  SecurityContextHolder.getContext().setAuthentication(authentication);
	  
	  model.addAttribute("endUser", new EndUser());
	  model.addAttribute("userId", currentUser.getUserId());
	  return "endUser_edit";
	  
	 }

	@RequestMapping("/updateUser")
	public String updateUser(Principal principal,Model model) {

		User user = userService.findByUsername(principal.getName());
		String returnPage = "";
		
		
		for (UserRole ur : user.getUserRoles()) {
			if (ur.getRole().getName().equals("ROLE_END_USER")) {
				EndUser endUser = endUserService.findByUser(user);
				model.addAttribute("endUser", endUser);
				model.addAttribute("username", user.getUsername());
				model.addAttribute("email", user.getEmail());
				model.addAttribute("userId", user.getUserId());
				returnPage = "endUser_edit";
			}else if (ur.getRole().getName().equals("ROLE_COMPANY")) {
				Company company = companyService.findByUser(user);
				model.addAttribute("company", company);
				model.addAttribute("username", user.getUsername());
				model.addAttribute("email", user.getEmail());
				model.addAttribute("userId", user.getUserId());
				returnPage = "company_edit";
			}
		}

		return returnPage;
	}

}
