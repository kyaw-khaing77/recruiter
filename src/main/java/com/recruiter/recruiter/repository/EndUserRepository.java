package com.recruiter.recruiter.repository;

import org.springframework.data.repository.CrudRepository;

import com.recruiter.recruiter.domain.EndUser;
import com.recruiter.recruiter.domain.User;

public interface EndUserRepository extends CrudRepository<EndUser, Long> {
    
    EndUser findByUser(User user);
}
