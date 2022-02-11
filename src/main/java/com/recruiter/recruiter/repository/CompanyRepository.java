package com.recruiter.recruiter.repository;

import org.springframework.data.repository.CrudRepository;

import com.recruiter.recruiter.domain.Company;
import com.recruiter.recruiter.domain.User;


public interface CompanyRepository extends CrudRepository<Company, Long> {
    

    Company findByUser(User user);

    Company findByCompanyName(String companyName);
  
    Company findByUser_userId(Long userId);
}
