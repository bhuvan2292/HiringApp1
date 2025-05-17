package com.bhuvan.hiringapp1.Repository;

import com.bhuvan.hiringapp1.Model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepo extends JpaRepository<Candidate, Long> {
    public Candidate findByName(String name);
    public Candidate findByEmail(String email);
}
