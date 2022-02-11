package com.recruiter.recruiter.service;

import java.util.Set;


import com.recruiter.recruiter.domain.User;
import com.recruiter.recruiter.domain.security.PasswordResetToken;
import com.recruiter.recruiter.domain.security.UserRole;

public interface UserService {
	
	PasswordResetToken getPasswordResetToken(final String token);
	
	void createPasswordResetTokenForUser(final User user,final String token);
	
	User findByEmail(String email);
	
	User findByUsername(String username);
	
	User findById(Long id);
	
	User createUser(User user,Set<UserRole> userRoles) throws Exception;
	
	User save(User user);

}
