package com.recruiter.recruiter;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.recruiter.recruiter.domain.EndUser;
import com.recruiter.recruiter.domain.User;
import com.recruiter.recruiter.domain.security.Role;
import com.recruiter.recruiter.domain.security.UserRole;
import com.recruiter.recruiter.service.EndUserService;
import com.recruiter.recruiter.service.UserService;
import com.recruiter.recruiter.utility.SecurityUtility;

@SpringBootApplication
@EnableJpaAuditing
public class RecruiterApplication implements CommandLineRunner {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EndUserService endUserService;

	public static void main(String[] args) {
		SpringApplication.run(RecruiterApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		
		
		
		/*
		 * User user=new User();
		 * 
		 * user.setUsername("admin");
		 * user.setPassword(SecurityUtility.passwordEncoder().encode("1234"));
		 * user.setEmail("kyawkhaing29581@gmail.com");
		 * 
		 * 
		 * Set<UserRole> userRoles=new HashSet<>(); Role role=new Role();
		 * role.setRoleId(3l); role.setName("ROLE_ADMIN");
		 * 
		 * userRoles.add(new UserRole(user, role));
		 * 
		 * userService.createUser(user, userRoles);
		 * 
		 * EndUser endUser = new EndUser(); endUser.setUser(user);
		 * 
		 * endUserService.save(endUser);
		 */
		 
		 
	}

}
