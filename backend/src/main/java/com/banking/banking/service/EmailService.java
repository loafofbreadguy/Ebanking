package com.banking.banking.service;

public interface EmailService {

    void sendVerificationEmail(String toEmail, String token);
}