package com.recruiter.recruiter.service.impl;

import java.util.List;

import com.recruiter.recruiter.domain.JobPost;
import com.recruiter.recruiter.repository.JobPostRepository;
import com.recruiter.recruiter.service.JobPostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class JobPostServiceImpl implements JobPostService{

    @Autowired
    private JobPostRepository postRepository;

    @Override
    public List<JobPost> findAll() {
        return (List<JobPost>) postRepository.findAll();
    }

    @Override
    public JobPost save(JobPost post) {
        return postRepository.save(post);
    }

    @Override
    public JobPost findById(Long id) {
        return postRepository.findById(id).get();
    }

    @Override
    public void removeById(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public List<JobPost> findFirst5ByStatusOrderByUpdatedAt(boolean status) {
        return postRepository.findFirst5ByStatusOrderByUpdatedAt(status);
    }

    @Override
    public Page<JobPost> findAllByStatusOrderByUpdatedAt(Integer pageNo, Integer pageSize, boolean status) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return postRepository.findAllByStatusOrderByUpdatedAt(status, paging);
    }

    @Override
    public List<JobPost> findByJobCategoryAndStatus(String jobCategory, boolean status) {
        return postRepository.findByJobCategoryAndStatus(jobCategory, status);
    }

    @Override
    public List<JobPost> findAllByStatus(boolean status) {
        return postRepository.findAllByStatus(status);
    }

	
    
}
