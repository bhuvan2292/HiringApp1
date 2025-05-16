package com.bhuvan.hiringapp1.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;

@Service
@Slf4j
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendMail(String to, String name, String status, File attachment) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true); // true enables multipart

        helper.setSubject("Job Offer from our Company");
        helper.setTo(to);

        // Prepare the HTML content using Thymeleaf
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("status", status);
        String htmlContent = templateEngine.process("Job_Offer", context);

        helper.setText(htmlContent, true);

        if (attachment != null && attachment.exists()) {
            FileSystemResource fileResource = new FileSystemResource(attachment);
            helper.addAttachment(attachment.getName(), fileResource);
        }

        javaMailSender.send(mimeMessage);

        log.info("Job Offer has been sent to: {}", to);
    }
}
