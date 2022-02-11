package com.recruiter.recruiter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recruiter.recruiter.domain.EndUser;
import com.recruiter.recruiter.domain.User;
import com.recruiter.recruiter.repository.EndUserRepository;
import com.recruiter.recruiter.service.EndUserService;

@Service
public class EndUserServiceImpl implements EndUserService{
	
	@Autowired
	private EndUserRepository endUserRepository;

	@Override
	public EndUser save(EndUser endUser) {
		return endUserRepository.save(endUser);
	}

	@Override
	public EndUser findByUser(User user) {
		return endUserRepository.findByUser(user);
	}

}
