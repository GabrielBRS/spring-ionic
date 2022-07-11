package com.gabrielsousa.service;

import org.springframework.mail.SimpleMailMessage;

import com.gabrielsousa.domain.Request;

public interface EmailService {

	void sendOrderConfirmationEmail(Request obj);
	
	void sendEmail(SimpleMailMessage msg);
}
