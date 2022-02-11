package com.recruiter.recruiter.repository;

import com.recruiter.recruiter.domain.Company;
import com.recruiter.recruiter.domain.Payment;

import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<Payment, Long> {
    
    Payment findByCompany(Company company);

    Payment findByCompany_companyId(Long companyId);
}
