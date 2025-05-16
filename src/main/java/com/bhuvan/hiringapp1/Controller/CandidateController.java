package com.bhuvan.hiringapp1.Controller;


import com.bhuvan.hiringapp1.Repository.CandidateRepo;
import com.bhuvan.hiringapp1.Service.CandidateService;
import com.bhuvan.hiringapp1.Model.Candidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private CandidateRepo candidateRepo;


    @PostMapping
    public String addCandidate(@RequestBody Candidate candidate) {
        candidateService.saveEntry(candidate);
        return "saved";
    }

    @PostMapping("api/{id}/status")
    public String addCandidate(@PathVariable Long id, @RequestBody Candidate candidate) {
        Candidate target_candidate = candidateRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("candidate not found with the id: "+id));
        //target_candidate found

        target_candidate.setStatus(candidate.getStatus());
        //update only the status

        candidateService.saveEntry(target_candidate);
        return "updated and saved";
    }

    @GetMapping("/api/candidates/hired")
    public List<Candidate> getCandidatesByHiredStatus() {
        List<Candidate> candidates_list = candidateService.getAllCandidates();
        // this will give us the whole candidate_list

        List<Candidate> hiredCandidates = new ArrayList<>();
        for(Candidate candidate : candidates_list) {
            if(candidate.getStatus().equals("offered")) {
                hiredCandidates.add(candidate);
            }
        }
        return hiredCandidates;

        // OR USING streamAPI

//        List<Candidate> hired_candidates = candidates_list.stream()
//                .filter( candidate -> "offered".equalsIgnoreCase(candidate.getStatus().name()))
//                .toList();// this will print all the hired candidates
//
//
//        return hired_candidates;
    }

    @GetMapping("/api/candidate/{id}")
    public Candidate getCandidateById(@PathVariable Long id) {
        return candidateService.getCandidate(id);
    }

    @GetMapping("/api/candidate/count")
    public Long getCandidateCount() {
        return candidateService.countCandidates();
    }

    @GetMapping
    public List<Candidate> getAllCandidates() {
        return candidateService.getAllCandidates();
    }

    @PutMapping("/api/candidates/{id}/onboard-status")
    public String updateCandidateStatus(@PathVariable Long id, @RequestBody Candidate candidate) {
        return candidateService.updateCandidate(id,candidate);
    }

}

