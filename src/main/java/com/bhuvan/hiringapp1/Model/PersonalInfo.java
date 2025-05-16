package com.bhuvan.hiringapp1.Model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PersonalInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String education_information;
    private String bank;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    private Candidate candidate;


}
