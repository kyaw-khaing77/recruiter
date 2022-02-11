package com.recruiter.recruiter.service.impl;

import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recruiter.recruiter.domain.Company;
import com.recruiter.recruiter.domain.EndUser;
import com.recruiter.recruiter.domain.User;
import com.recruiter.recruiter.domain.security.PasswordResetToken;
import com.recruiter.recruiter.domain.security.UserRole;
import com.recruiter.recruiter.repository.CompanyRepository;
import com.recruiter.recruiter.repository.EndUserRepository;
import com.recruiter.recruiter.repository.PasswordResetTokenRepository;
import com.recruiter.recruiter.repository.RoleRepository;
import com.recruiter.recruiter.repository.UserRepository;
import com.recruiter.recruiter.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	private static final Logger LOG=LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private EndUserRepository endUserRepository;

	@Override
	public PasswordResetToken getPasswordResetToken(final String token) {
		return passwordResetTokenRepository.findByToken(token);	
	}

	@Override
	public void createPasswordResetTokenForUser(final User user,final String token) {
		final PasswordResetToken myToken = new PasswordResetToken(token, user);
		
		passwordResetTokenRepository.save(myToken);
	}

	@Override
	public User findByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(email);
	}

	@Override
	@Transactional
	public User createUser(User user, Set<UserRole> userRoles){
		User localUser = userRepository.findByUsername(user.getUsername());
		
		if(localUser != null) {
			LOG.info("user {} already exists. Nothing will be done.", user.getUsername());
		} else {
			String role = "";
			for (UserRole ur : userRoles) {
				roleRepository.save(ur.getRole());
				role = ur.getRole().getName();
			}
			
			user.getUserRoles().addAll(userRoles);
			localUser = userRepository.save(user);
			
			if (role.equals("ROLE_END_USER")) {
			   EndUser endUser = new EndUser();
			   endUser.setUser(localUser);
			   endUserRepository.save(endUser);
			} else if (role.equals("ROLE_COMPANY")) {
		       Company company = new Company();
		       company.setUser(localUser);
		       companyRepository.save(company);
			}
		}
		
		return localUser;
	}

	@Override
	public User save(User user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}

	@Override
	public User findByUsername(String username) {
		// TODO Auto-generated method stub
		return userRepository.findByUsername(username);
	}

	@Override
	public User findById(Long id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id).get();
	}

}
