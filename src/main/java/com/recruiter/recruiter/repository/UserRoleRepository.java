package com.recruiter.recruiter.repository;

import org.springframework.data.repository.CrudRepository;

import com.recruiter.recruiter.domain.security.UserRole;

public interface UserRoleRepository extends CrudRepository<UserRole, Long>{

}

