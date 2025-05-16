package com.bhuvan.hiringapp1.Repository;

import com.bhuvan.hiringapp1.Model.PersonalInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalInfoRepo extends JpaRepository<PersonalInfo, Long> {
}
