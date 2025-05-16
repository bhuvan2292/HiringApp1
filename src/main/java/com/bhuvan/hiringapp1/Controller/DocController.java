package com.bhuvan.hiringapp1.Controller;

import com.bhuvan.hiringapp1.Model.Doc;
import com.bhuvan.hiringapp1.Service.DocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DocController {
    @Autowired
    private DocService docService;

    @GetMapping("/all-documents")
    public List<Doc> getAllDocuments() {
        return docService.getDocuments();
    }

    @PostMapping("/api/candidates/{id}/upload-document")
    public ResponseEntity<Doc> addDocumentToCandidate(@PathVariable Long id, @RequestBody Doc doc){
        return docService.addDocumentToCandidate(doc, id) ;
    }

    @PostMapping("/document")
    public ResponseEntity<Doc> add_document_normally(@RequestBody Doc doc){
        return docService.add_document_normally(doc) ;
    }

    @PutMapping("/api/candidates/{id}/verify-document")
    public String verify_document(@PathVariable Long id){
        return docService.verifyDocument(id)+" : document verified";
    }

}

