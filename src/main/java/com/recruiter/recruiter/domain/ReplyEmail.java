package com.recruiter.recruiter.domain;

public class ReplyEmail {
	
	 private String emailSubject;
	 private String interviewTime;
	 private String interviewLocation;
	 private String emailContent1;
	 private String emailContent2;
	 
	 public ReplyEmail() {
		 
	 }
	 
	public ReplyEmail(String emailSubject, String interviewTime, String interviewLocation, String emailContent1,
			String emailContent2) {
		this.emailSubject = emailSubject;
		this.interviewTime = interviewTime;
		this.interviewLocation = interviewLocation;
		this.emailContent1 = emailContent1;
		this.emailContent2 = emailContent2;
	}
	public String getEmailSubject() {
		return emailSubject;
	}
	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}
	public String getInterviewTime() {
		return interviewTime;
	}
	public void setInterviewTime(String interviewTime) {
		this.interviewTime = interviewTime;
	}
	public String getInterviewLocation() {
		return interviewLocation;
	}
	public void setInterviewLocation(String interviewLocation) {
		this.interviewLocation = interviewLocation;
	}
	public String getEmailContent1() {
		return emailContent1;
	}
	public void setEmailContent1(String emailContent1) {
		this.emailContent1 = emailContent1;
	}
	public String getEmailContent2() {
		return emailContent2;
	}
	public void setEmailContent2(String emailContent2) {
		this.emailContent2 = emailContent2;
	}
	 
	 

}
