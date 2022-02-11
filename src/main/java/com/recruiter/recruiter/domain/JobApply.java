package com.recruiter.recruiter.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

@Entity
public class JobApply {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "jobApply_id")
	private Long jobApplyId;
	
	@Transient
	private String emailSubject;
	
	@Transient
	private String contentAboutJob;
	
	@Transient
	private String contentAboutCompany;
	
	@Transient
	private MultipartFile[] attachFiles;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "post_id")
	private JobPost jobPost;
	
	public JobApply(User user, JobPost jobPost) {
		this.user = user;
		this.jobPost = jobPost;
	}

	public JobApply() {
		super();
	}
	
	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public String getContentAboutJob() {
		return contentAboutJob;
	}

	public void setContentAboutJob(String contentAboutJob) {
		this.contentAboutJob = contentAboutJob;
	}

	public String getContentAboutCompany() {
		return contentAboutCompany;
	}

	public void setContentAboutCompany(String contentAboutCompany) {
		this.contentAboutCompany = contentAboutCompany;
	}

	public MultipartFile[] getAttachFiles() {
		return attachFiles;
	}

	public void setAttachFiles(MultipartFile[] attachFiles) {
		this.attachFiles = attachFiles;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public JobPost getJobPost() {
		return jobPost;
	}

	public void setJobPost(JobPost jobPost) {
		this.jobPost = jobPost;
	}
	
	

}
