package com.recruiter.recruiter.service;

import java.util.List;

import com.recruiter.recruiter.domain.JobApply;
import com.recruiter.recruiter.domain.JobPost;
import com.recruiter.recruiter.domain.User;

public interface JobApplyService {
	
	List<User> findUserByJobPostId(JobPost jobPost);
	
	List<JobPost> findJobPostByUserId(User user);
	
    JobApply save(JobApply jobApply);
    
    JobApply alreadyApply(User user,JobPost jobPost);
}
