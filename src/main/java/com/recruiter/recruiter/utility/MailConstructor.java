package com.recruiter.recruiter.utility;

import java.io.File;
import java.util.Locale;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.recruiter.recruiter.domain.EndUser;
import com.recruiter.recruiter.domain.JobApply;
import com.recruiter.recruiter.domain.JobPost;
import com.recruiter.recruiter.domain.ReplyEmail;
import com.recruiter.recruiter.domain.User;

@Component
public class MailConstructor {
	
	@Autowired
	private Environment env;

	@Autowired
	private TemplateEngine templateEngine;
	
	public SimpleMailMessage constructResetTokenEmail(String contextPath,Locale locale,String token,User user,String password){
		String url = contextPath + "/newUser?token="+token;
		
		String message = "\nPlease click on this link to verify your email and "
				+ "edit your personal information.Your Password is : \n"+password;
		
		SimpleMailMessage email=new SimpleMailMessage();
		email.setTo(user.getEmail());
		email.setFrom(env.getProperty("support.email"));
		email.setSubject("Recruiter - New User");
		email.setText(url+message);
		return email;
		
	}
	
	public MimeMessagePreparator applyJobEmail(Locale locale,String userDeatilLink,JobApply jobApply ,EndUser endUser) {
		Context context = new Context();
		context.setVariable("userName", jobApply.getUser().getUsername());
		context.setVariable("userEmail", jobApply.getUser().getEmail());
		context.setVariable("userAddress", endUser.getAddress());
		context.setVariable("userPhone", endUser.getPhone());
		context.setVariable("companyName", jobApply.getJobPost().getCompany().getUser().getUsername());
		context.setVariable("contentAboutJob", jobApply.getContentAboutJob());
		context.setVariable("contentAboutCompany", jobApply.getContentAboutCompany());

		String text = templateEngine.process("apply_job_email_format", context);
		
		
		MimeMessagePreparator mimeMessagePreparator = new MimeMessagePreparator() {

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper email = new MimeMessageHelper(mimeMessage, true);
				email.setTo(jobApply.getJobPost().getCompany().getUser().getEmail());
				email.setSubject(jobApply.getEmailSubject());
				email.setText(text, true);
				email.setFrom(new InternetAddress(env.getProperty("support.email")));

				if(jobApply.getAttachFiles().length > 1) {
					
					for (MultipartFile filename : jobApply.getAttachFiles()) {
						FileSystemResource fr = new FileSystemResource(
								new File("src/main/resources/static/applyfiles/" + filename.getOriginalFilename()));
						email.addAttachment(filename.getOriginalFilename(), fr);
					}
				}
			}
		};

		return mimeMessagePreparator;
	}
	
	public MimeMessagePreparator interviewInvitationEmail(Locale locale,String userDeatilLink,ReplyEmail replyEmail,User user,JobPost post) {
		Context context = new Context();

		context.setVariable("companyName", post.getCompany().getUser().getUsername());
		context.setVariable("companyEmail", post.getCompany().getUser().getEmail());
		context.setVariable("companyPhone", post.getCompany().getCompanyPhone());
		context.setVariable("companyAddress", post.getCompany().getCompanyAddress());
		context.setVariable("userName", user.getUsername());
		context.setVariable("jobTitle", post.getJobTitle());
		context.setVariable("interviewTimeString",replyEmail.getInterviewTime());
		context.setVariable("interviewLocation", replyEmail.getInterviewLocation());
		context.setVariable("emailContent1", replyEmail.getEmailContent1());
		context.setVariable("emailContent2", replyEmail.getEmailContent2());
		
		String text = templateEngine.process("interview_invitation_email_format", context);
		
		
		MimeMessagePreparator mimeMessagePreparator = new MimeMessagePreparator() {

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper email = new MimeMessageHelper(mimeMessage, true);
				email.setTo(user.getEmail());
				email.setSubject(replyEmail.getEmailSubject());
				email.setText(text, true);
				email.setFrom(new InternetAddress(env.getProperty("support.email")));
			}
		};

		return mimeMessagePreparator;
	}
}
