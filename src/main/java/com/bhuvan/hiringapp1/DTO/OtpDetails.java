package com.bhuvan.hiringapp1.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpDetails {
    private Integer otp ;
    private LocalDateTime expiry ;

}