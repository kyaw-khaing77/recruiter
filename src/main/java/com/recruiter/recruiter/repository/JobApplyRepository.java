package com.recruiter.recruiter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.recruiter.recruiter.domain.JobApply;
import com.recruiter.recruiter.domain.JobPost;
import com.recruiter.recruiter.domain.User;

public interface JobApplyRepository extends JpaRepository<JobApply, Long>{
	
	@Modifying
	@Query("select user from JobApply j where j.jobPost = ?1")
	List<User> findUserByJobPostId(JobPost jobPost);
	
	@Modifying
	@Query("select jobPost from JobApply j where j.user = ?1")
	List<JobPost> findJobPostByUserId(User user);
	
	@Query("select j from JobApply j where j.user = ?1 and j.jobPost =?2")
	JobApply alreadyApply(User user,JobPost jobPost);

}
