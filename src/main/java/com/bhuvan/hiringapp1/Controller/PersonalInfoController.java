package com.bhuvan.hiringapp1.Controller;

import com.bhuvan.hiringapp1.Advice.HandleException;
import com.bhuvan.hiringapp1.Config.EmailConfig;
import com.bhuvan.hiringapp1.DTO.BankInfoDTO;
import com.bhuvan.hiringapp1.DTO.EducationInfoDTO;
import com.bhuvan.hiringapp1.DTO.EmailStatus;
import com.bhuvan.hiringapp1.Exception.InvalidEmailException;
import com.bhuvan.hiringapp1.Model.Candidate;
import com.bhuvan.hiringapp1.Model.PersonalInfo;
import com.bhuvan.hiringapp1.Service.PersonalInfoService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitTemplateConfigurer;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class PersonalInfoController {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private PersonalInfoService personalInfoService;

    // DTO means Data transfer object i.e transferring data between the layers of the application ....
    @PutMapping("/api/candidates/{id}/personal-info")
    public String addPersonalInfoToCandidate(@RequestBody PersonalInfo personalInfo, @PathVariable Long id) {
        return personalInfoService.addPersonalInfoToCandidate(personalInfo, id);
    }

    @PutMapping("/api/candidates/{id}/educational-info")
    public Candidate updateEducationalInfo(@RequestBody EducationInfoDTO educationInfoDTO, @PathVariable Long id) {
        return personalInfoService.updateEducationInfo(id, educationInfoDTO);
    }

    @PutMapping("/api/candidates/{id}/bank-info")
    public Candidate updateBankInfo(@RequestBody BankInfoDTO bankInfoDTO, @PathVariable Long id) {
        return personalInfoService.updateBankInfoDTO(id,bankInfoDTO);
    }

    //for rabbitmq -> publishing message to queue
    @PostMapping("/api/candidates/{id}/notify-job-offer")
    public String queuingMessages(@RequestBody Candidate candidate, @PathVariable Long id) throws HandleException {
        String targetEmail = candidate.getEmail();

        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$\n" ;
        Pattern pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE) ;
        Matcher matcher = pattern.matcher(targetEmail) ;

        if(matcher.matches()){
            EmailStatus emailStatus = new EmailStatus(targetEmail,"Hi, this is a queued e-mail" ) ;
            rabbitTemplate.convertAndSend(EmailConfig.EXCHANGE,EmailConfig.ROUTING_KEY,emailStatus);
            return "success";
        }
        else{
            throw new InvalidEmailException("The email address entered is invalid");
        }

    }

}
