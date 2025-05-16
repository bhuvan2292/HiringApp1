package com.bhuvan.hiringapp1.Service;

import com.bhuvan.hiringapp1.Model.Candidate;
import com.bhuvan.hiringapp1.Model.Doc;
import com.bhuvan.hiringapp1.Repository.CandidateRepo;
import com.bhuvan.hiringapp1.Repository.DocRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class DocService {
    @Autowired
    private DocRepo docRepo;

    @Autowired
    private CandidateRepo candidateRepo;

    public List<Doc> getDocuments(){
        return docRepo.findAll();
    }

    @Transactional
    public ResponseEntity<Doc> addDocumentToCandidate(Doc doc,Long id){
        try{
            Candidate candidate = candidateRepo.findById(id).orElseThrow(
                    () -> new RuntimeException("Candidate not found")
            );
            doc.setCandidate(candidate);
            docRepo.save(doc);        // saving in the doc table

            candidate.getDocuments().add(doc);     // adding in the candidate
            candidateRepo.save(candidate);         // saving in the candidate table
            return new ResponseEntity<>(doc, HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Boolean verifyDocument(Long id ){
        Doc doc = docRepo.findById(id).orElseThrow(
                () -> new RuntimeException("Document not found")
        );

        String regex = "^[\\w,\\s-]+\\.(pdf|docx|jpg|jpeg|png)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(doc.getFileName());

        return matcher.matches();
    }

    public ResponseEntity<Doc> add_document_normally(Doc doc) {
        try{
            docRepo.save(doc) ;
            return new ResponseEntity<>(HttpStatus.OK) ;
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST) ;
        }
    }
}
