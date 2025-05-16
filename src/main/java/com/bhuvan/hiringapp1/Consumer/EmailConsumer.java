package com.bhuvan.hiringapp1.Consumer;

import com.bhuvan.hiringapp1.Config.EmailConfig;
import com.bhuvan.hiringapp1.DTO.EmailStatus;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class EmailConsumer {
    @RabbitListener(queues = EmailConfig.QUEUE)
    public void consumeMessageFromQueue(EmailStatus emailStatus){
        if(isValidEmail(emailStatus)){
            System.out.println("Email consumed successfully" + emailStatus.getEmail());
        }
        else{
            throw new IllegalArgumentException("Invalid email status"+ emailStatus.getEmail());
        }
    }

    public boolean isValidEmail(EmailStatus emailStatus){
        String target_email = emailStatus.getEmail();
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        return Pattern.compile(regex,Pattern.CASE_INSENSITIVE)
                .matcher(target_email)
                .matches() ;
    }

    @RabbitListener(queues = EmailConfig.DEAD_QUEUE)
    public void putMessageInQueue(EmailStatus emailStatus){
        System.out.println("Processed in dead_queue: "+emailStatus.getEmail());
    }


}
