package com.recruiter.recruiter.domain;


import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.recruiter.recruiter.common.JobExpireStatus;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "jobpost")
public class JobPost {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long postId;

	private String jobTitle;

	private String jobCategory;

	private String jobLevel;

	private String jobType;

	private Integer minSalary;

	private Integer maxSalary;

	private String ageLimit;

	private Integer employeeLimit;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date deadline;

	private String jobHour;

	private String jobDay;

	private String jobLocation;

	@Column(columnDefinition = "text")
	private String jobDescription;

	@Column(columnDefinition = "text")
	private String jobRequirement;

	@Column(columnDefinition = "text")
	private String jobBenefit;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at", nullable = false, updatable = false)
	@CreatedDate
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	@LastModifiedDate
	private Date updatedAt;
//	@Enumerated(EnumType.ORDINAL)
//	private JobStatus jobStatus;

	@Enumerated(EnumType.ORDINAL)
	private JobExpireStatus expireStatus;

	private boolean status = false;

	@ManyToOne
	private Company company;

	@ManyToOne
	private Payment payment;

	private LocalDateTime approvedDate;

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getJobCategory() {
		return jobCategory;
	}

	public void setJobCategory(String jobCategory) {
		this.jobCategory = jobCategory;
	}

	public String getJobLevel() {
		return jobLevel;
	}

	public void setJobLevel(String jobLevel) {
		this.jobLevel = jobLevel;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public Integer getMinSalary() {
		return minSalary;
	}

	public void setMinSalary(Integer minSalary) {
		this.minSalary = minSalary;
	}

	public Integer getMaxSalary() {
		return maxSalary;
	}

	public void setMaxSalary(Integer maxSalary) {
		this.maxSalary = maxSalary;
	}

	public String getAgeLimit() {
		return ageLimit;
	}

	public void setAgeLimit(String ageLimit) {
		this.ageLimit = ageLimit;
	}

	public Integer getEmployeeLimit() {
		return employeeLimit;
	}

	public void setEmployeeLimit(Integer employeeLimit) {
		this.employeeLimit = employeeLimit;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public String getJobHour() {
		return jobHour;
	}

	public void setJobHour(String jobHour) {
		this.jobHour = jobHour;
	}

	public String getJobDay() {
		return jobDay;
	}

	public void setJobDay(String jobDay) {
		this.jobDay = jobDay;
	}

	public String getJobLocation() {
		return jobLocation;
	}

	public void setJobLocation(String jobLocation) {
		this.jobLocation = jobLocation;
	}

	public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	public String getJobRequirement() {
		return jobRequirement;
	}

	public void setJobRequirement(String jobRequirement) {
		this.jobRequirement = jobRequirement;
	}

	public String getJobBenefit() {
		return jobBenefit;
	}

	public void setJobBenefit(String jobBenefit) {
		this.jobBenefit = jobBenefit;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public JobExpireStatus getExpireStatus() {
		return expireStatus;
	}

	public void setExpireStatus(JobExpireStatus expireStatus) {
		this.expireStatus = expireStatus;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public LocalDateTime getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(LocalDateTime approvedDate) {
		this.approvedDate = approvedDate;
	}

}
