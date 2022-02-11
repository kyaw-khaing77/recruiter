package com.recruiter.recruiter.domain;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.web.multipart.MultipartFile;

@Entity
public class Company {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "companyId", nullable = false, updatable = false)
    private Long companyId;
    private String companyName;
    private String companyPhone;
    private String companyWebsite;
    private String companyType;
    private String noOfEmployee;
    
    @Column(columnDefinition = "text")
    private String companyAddress;
    
    @Transient
    private MultipartFile companyLogo;
    
    @Transient
    private List<MultipartFile> companyFeaturePhotos;
    
    @Column(columnDefinition = "text")
    private String companyMission;
    
    @Column(columnDefinition = "text")
    private String companyVision;
    

	@OneToOne(targetEntity = User.class,fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;
    
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<JobPost> post = new ArrayList<JobPost>();

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "company")
    private Payment payment;
    
    
    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getCompanyId() {
        return companyId;
    }
    
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public List<JobPost> getPost() {
        return post;
    }
    
    /**
    * @param post the post to set
    */
    public void setPost(List<JobPost> post) {
        this.post = post;
    }
    
    /**
    * @return String return the companyPhone
    */
    public String getCompanyPhone() {
        return companyPhone;
    }
    
    /**
    * @param companyPhone the companyPhone to set
    */
    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }
    
    /**
    * @return String return the companyWebsite
    */
    public String getCompanyWebsite() {
        return companyWebsite;
    }
    
    /**
    * @param companyWebsite the companyWebsite to set
    */
    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }
    
    /**
    * @return String return the companyType
    */
    public String getCompanyType() {
        return companyType;
    }
    
    /**
    * @param companyType the companyType to set
    */
    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }
    
    /**
    * @return String return the noOfEmployee
    */
    public String getNoOfEmployee() {
        return noOfEmployee;
    }
    
    /**
    * @param noOfEmployee the noOfEmployee to set
    */
    public void setNoOfEmployee(String noOfEmployee) {
        this.noOfEmployee = noOfEmployee;
    }
    
    /**
    * @return String return the companyAddress
    */
    public String getCompanyAddress() {
        return companyAddress;
    }
    
    /**
    * @param companyAddress the companyAddress to set
    */
    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }
    
    /**
    * @return MultipartFile return the companyLogo
    */
    public MultipartFile getCompanyLogo() {
        return companyLogo;
    }
    
    /**
    * @param companyLogo the companyLogo to set
    */
    public void setCompanyLogo(MultipartFile companyLogo) {
        this.companyLogo = companyLogo;
    }
    
    /**
    * @return String return the companyMission
    */
    public String getCompanyMission() {
        return companyMission;
    }
    
    /**
    * @param companyMission the companyMission to set
    */
    public void setCompanyMission(String companyMission) {
        this.companyMission = companyMission;
    }
    
    /**
    * @return String return the companyVision
    */
    public String getCompanyVision() {
        return companyVision;
    }
    
    /**
    * @param companyVision the companyVision to set
    */
    public void setCompanyVision(String companyVision) {
        this.companyVision = companyVision;
    }
    
    /**
    * @return Payment return the payment
    */
    public Payment getPayment() {
        return payment;
    }
    
    /**
    * @param payment the payment to set
    */
    public void setPayment(Payment payment) {
        this.payment = payment;
    }
    public List<MultipartFile> getCompanyFeaturePhotos() {
        return companyFeaturePhotos;
    }

    /**
     * @param companyFeaturePhotos the companyFeaturePhotos to set
     */
    public void setCompanyFeaturePhotos(List<MultipartFile> companyFeaturePhotos) {
        this.companyFeaturePhotos = companyFeaturePhotos;
    }
	
    /**
    * @return String return the companyName
    */
    public String getCompanyName() {
        return companyName;
    }
    
    /**
    * @param companyName the companyName to set
    */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}
