package com.recruiter.recruiter.repository;

import java.util.List;

import com.recruiter.recruiter.domain.JobPost;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface JobPostRepository extends PagingAndSortingRepository<JobPost, Long> {
  
    List<JobPost> findFirst5ByStatusOrderByUpdatedAt(boolean status);

    List<JobPost> findAllByStatusOrderByUpdatedAtDesc(boolean status);

    List<JobPost> findAllByStatus(boolean status);
    
    List<JobPost> findByJobCategoryAndStatus(String jobCategory, boolean status);

    Page<JobPost> findAllByStatusOrderByUpdatedAt(boolean status, Pageable paging);

}
