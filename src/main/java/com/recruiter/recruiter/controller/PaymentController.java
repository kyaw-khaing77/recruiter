package com.recruiter.recruiter.controller;

import java.security.Principal;

import com.recruiter.recruiter.domain.Company;
import com.recruiter.recruiter.domain.Payment;
import com.recruiter.recruiter.domain.User;
import com.recruiter.recruiter.service.CompanyService;
import com.recruiter.recruiter.service.PaymentService;
import com.recruiter.recruiter.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @Autowired
    UserService userService;

    @Autowired
    CompanyService companyService;

    @RequestMapping("/payment")
    public String payment(Model model, Principal principal) {
        // Payment payment = new Payment();
        User user = userService.findByUsername(principal.getName());
        Company company = companyService.findByUser_Id(user.getUserId());
        Payment payment = paymentService.findByCompany_companyId(company.getCompanyId());
        if(payment == null){
            payment = new Payment();
        }
        model.addAttribute("payment",payment);
        return "company_payment";
    }

    @PostMapping("/savePayment")
    public String savePayment(@ModelAttribute("payment") Payment payment, Principal principal){
        // User user = userService.findByUsername(principal.getName());
        User user = userService.findByUsername(principal.getName());
        Company company = companyService.findByUser_Id(user.getUserId());
        Payment currentPayment = paymentService.findByCompany_companyId(company.getCompanyId());
        
        if(currentPayment != null){
            currentPayment.setPaymentType(payment.getPaymentType());
            currentPayment.setCardName(payment.getCardName());
            currentPayment.setCardNumber(payment.getCardNumber());
            currentPayment.setExpiryMonth(payment.getExpiryMonth());
            currentPayment.setExpiryYear(payment.getExpiryYear());
            currentPayment.setCvc(payment.getCvc());
            currentPayment.setHolderName(payment.getHolderName());
        } else {
            currentPayment = payment;
        }

        paymentService.save(currentPayment, company);
        return "index";
    }
    
    
}
