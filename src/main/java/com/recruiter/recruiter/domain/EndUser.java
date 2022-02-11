package com.recruiter.recruiter.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

@Entity
public class EndUser {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,updatable = false)
	private Long endUserId;

	private String firstName;
	private String lastName;
	private String endUserName;

	private String gender;
	private Integer age;
	private String phone;
	private String websiteUrl;
	private String linkedIn;
	@Column(columnDefinition = "text")
	private String education;
	@Column(columnDefinition = "text")
	private String bio;
	@Column(columnDefinition = "text")
	private String address;
    @Transient
    private MultipartFile userPicture;
    
	@OneToOne(targetEntity = User.class,fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	public String getEndUserName() {
		return endUserName;
	}

	public void setEndUserName(String endUserName) {
		this.endUserName = endUserName;
	}

	public Long getEndUserId() {
		return endUserId;
	}

	public void setEndUserId(Long endUserId) {
		this.endUserId = endUserId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWebsiteUrl() {
		return websiteUrl;
	}

	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}

	public String getLinkedIn() {
		return linkedIn;
	}

	public void setLinkedIn(String linkedIn) {
		this.linkedIn = linkedIn;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public MultipartFile getUserPicture() {
		return userPicture;
	}

	public void setUserPicture(MultipartFile userPicture) {
		this.userPicture = userPicture;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "EndUser [endUserId=" + endUserId + ", firstName=" + firstName + ", lastName=" + lastName + ", gender="
				+ gender + ", age=" + age + ", phone=" + phone + ", websiteUrl=" + websiteUrl + ", linkedIn=" + linkedIn
				+ ", education=" + education + ", bio=" + bio + ", address=" + address + ", userPicture=" + userPicture
				+ ", user=" + user + "]";
	}

	
	
}
