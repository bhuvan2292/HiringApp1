package com.bhuvan.hiringapp1.Service;


import com.bhuvan.hiringapp1.Repository.CandidateRepo;
import com.bhuvan.hiringapp1.Repository.DocRepo;
import com.bhuvan.hiringapp1.Model.ApplicationStatus;
import com.bhuvan.hiringapp1.Model.Candidate;
import com.bhuvan.hiringapp1.Model.Doc;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateService {
    @Autowired
    private CandidateRepo candidateRepo;

    @Autowired
    private DocRepo docRepo;

    @Transactional  // ACID PROPERTIES OF DBMS
    public void saveEntry(Doc doc, String candidate_name) {
        Candidate target_candidate = candidateRepo.findByName(candidate_name);

        Doc saved_doc = docRepo.save(doc);   // doc saved in the doc-database

        target_candidate.getDocuments().add(saved_doc);  //adding the doc in the targetCandidate database as well

    }

    public void saveEntry(Candidate candidate) {
        candidateRepo.save(candidate);
    }

    public Candidate getCandidate(Long id) {
        return candidateRepo.findById(id).get();
        // for find a candidate by the id only
    }

    public List<Candidate> getAllCandidates() {
        return candidateRepo.findAll();
        // to get the list of all the candidates
    }

    public long countCandidates() {
        return candidateRepo.count();
        // to get the count of the candidates
    }

    public List<Doc> getALlDocs() {
        return docRepo.findAll();
        // for listing all the documents
    }

    public String setStatus(Candidate candidate, ApplicationStatus status) {
        candidate.setStatus(status);
        return candidate.getName() +" status updated";
    }

    public String validateCandidate(Candidate candidate) throws Exception{
        List<Doc> documents = candidate.getDocuments();
        if(documents.isEmpty()) {
            throw new Exception("Candidate must submit required Documents");
        }

        boolean hasResume = documents.stream().anyMatch(doc -> "Resume".equalsIgnoreCase(doc.getFileName()));
        //documents -validation
        boolean hasID = documents.stream()
                .anyMatch(doc -> "ID".equalsIgnoreCase(doc.getFileName()));
        //personal-info validation

        if(hasResume && hasID) {
            return "Candidate already exists";
        }
        else{
            throw new Exception("Candidate must have a resume or ID");
        }
    }

    public String updateCandidate(Long id,Candidate candidate) {
        Candidate target_candidate = candidateRepo.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Candidate not found"));
        target_candidate.setStatus(candidate.getStatus());
        candidateRepo.save(target_candidate);
        return "Candidate updated";
    }
}

