package com.bhuvan.hiringapp1.Controller;

import com.bhuvan.hiringapp1.DTO.OtpDetails;
import com.bhuvan.hiringapp1.Model.Candidate;
import com.bhuvan.hiringapp1.Repository.CandidateRepo;
import com.bhuvan.hiringapp1.Service.OTPService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Random;

@RequestMapping("/user")
@RestController
public class Forgot_passController {
    Random random = new Random(1000) ;
    @Autowired
    private CandidateRepo candidateRepo;

    @Autowired
    private JavaMailSender javaMailSender ;

    @Autowired
    private OTPService otpService;

    @PostMapping("/forgot-password")
    public String forgot_password(@RequestParam String email) throws MessagingException {
        Candidate candidate = candidateRepo.findByEmail(email) ; // fetching the candidate

        if(candidate == null){
            throw new IllegalArgumentException("candidate not found") ;
        }

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage() ;
        OtpDetails details = otpService.generate_Otp(email) ;

        simpleMailMessage.setSubject("Otp for password-reset !");
        simpleMailMessage.setText(String.valueOf(details.getOtp()));
        simpleMailMessage.setTo(email);

        javaMailSender.send(simpleMailMessage);
        System.out.println("Mail sent to: "+email +"with otp: "+details.getOtp());
        return "Otp sent to your mail !" ;
    }

    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String email, @RequestParam String otp){
        boolean isValid = otpService.validateOTP(email,otp) ;
        otpService.isEmailVerified(email) ;
        return (isValid)?"Otp verified":"Sorry! Its not correct" ;
    }

    @PostMapping("/reset-password")
    public String resetPass(@RequestParam String email, @RequestParam String password){
        //fetching the candidate
        Candidate candidate = candidateRepo.findByEmail(email) ;
        if(candidate == null) {
            return "Candidate not Found" ;
        }

        if (!otpService.isEmailVerified(email)){
            return "user's otp is not verified !" ;
        }

        //else -> candidate is found

        candidate.getPersonalInfo().setPassword(password) ;
        candidateRepo.save(candidate);

        System.out.println("password changed to: "+password);

        return "password updated" ;
    }

}
