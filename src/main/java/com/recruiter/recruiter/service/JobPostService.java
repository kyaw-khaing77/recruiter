package com.recruiter.recruiter.service;

import java.util.List;

import com.recruiter.recruiter.domain.JobPost;

import org.springframework.data.domain.Page;

public interface JobPostService {
    List<JobPost> findAll();

    List<JobPost> findFirst5ByStatusOrderByUpdatedAt(boolean status);

    Page<JobPost> findAllByStatusOrderByUpdatedAt(Integer pageNo, Integer pageSize, boolean status);

    List<JobPost> findAllByStatus(boolean status);

    JobPost save(JobPost post);

    JobPost findById(Long id);

    void removeById(Long id);
    
    List<JobPost> findByJobCategoryAndStatus(String jobCategory, boolean status);
}
