package com.recruiter.recruiter.repository;

import org.springframework.data.repository.CrudRepository;

import com.recruiter.recruiter.domain.security.Role;

public interface RoleRepository extends CrudRepository<Role,Long>{

	Role findByName(String name);
	

}
