package com.recruiter.recruiter.service;

import com.recruiter.recruiter.domain.EndUser;
import com.recruiter.recruiter.domain.User;

public interface EndUserService {

    EndUser save(EndUser endUser);
    
    EndUser findByUser(User user);
    
}
