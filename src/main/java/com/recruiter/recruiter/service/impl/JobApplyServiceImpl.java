package com.recruiter.recruiter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recruiter.recruiter.domain.JobApply;
import com.recruiter.recruiter.domain.JobPost;
import com.recruiter.recruiter.domain.User;
import com.recruiter.recruiter.repository.JobApplyRepository;
import com.recruiter.recruiter.service.JobApplyService;

@Service
public class JobApplyServiceImpl implements JobApplyService{
	
	@Autowired
	private JobApplyRepository jobApplyRepository;

	@Override
	public List<User> findUserByJobPostId(JobPost jobPost) {
		return jobApplyRepository.findUserByJobPostId(jobPost);
	}

	@Override
	public List<JobPost> findJobPostByUserId(User user) {
		return jobApplyRepository.findJobPostByUserId(user);
	}

	@Override
	public JobApply save(JobApply jobApply) {
		return jobApplyRepository.save(jobApply);
	}

	@Override
	public JobApply alreadyApply(User user, JobPost jobPost) {
		return jobApplyRepository.alreadyApply(user, jobPost);
	}

}
