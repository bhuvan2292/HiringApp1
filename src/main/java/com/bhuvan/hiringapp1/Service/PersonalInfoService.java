package com.bhuvan.hiringapp1.Service;

import com.bhuvan.hiringapp1.DTO.BankInfoDTO;
import com.bhuvan.hiringapp1.DTO.EducationInfoDTO;
import com.bhuvan.hiringapp1.Model.Candidate;
import com.bhuvan.hiringapp1.Model.PersonalInfo;
import com.bhuvan.hiringapp1.Repository.CandidateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalInfoService {

    @Autowired
    private CandidateRepo candidateRepo;


    public String addPersonalInfoToCandidate(PersonalInfo personalInfo, Long id) {
        Candidate candidate = candidateRepo.findById(id).orElseThrow(
                () -> new RuntimeException("Candidate with id " + id + " not found")
        );
        candidate.setPersonalInfo(personalInfo);

        candidateRepo.save(candidate);
        return "updated and saved";
    }

    //updating educationalInfo by mapping educationInfoDTO to the personalInfo

    public Candidate updateEducationInfo(Long id, EducationInfoDTO educationInfoDTO) {
        Candidate candidate = candidateRepo.findById(id).orElseThrow(
                () -> new RuntimeException("Candidate with id " + id + " not found")
        );
        PersonalInfo personalInfo = candidate.getPersonalInfo();

        if(personalInfo == null) {
            personalInfo = new PersonalInfo();  //new object created and personalInfo is referring to the new object
            personalInfo.setCandidate(candidate);
            candidate.setPersonalInfo(personalInfo);  //Now candidates personalInfo set to new object
        }
        personalInfo.setEducation_information(educationInfoDTO.getEducational_info());
        candidateRepo.save(candidate);
        return candidate;
    }

    //updating  by mapping bankInfoDTO to the personalInfo
    public Candidate updateBankInfoDTO(Long id, BankInfoDTO bankInfoDTO) {
        Candidate candidate = candidateRepo.findById(id).orElseThrow(
                () -> new RuntimeException("Candidate with id " + id + " not found")
        );
        PersonalInfo personalInfo = candidate.getPersonalInfo();
        if(personalInfo == null) {
            personalInfo = new PersonalInfo();  //new object created and personalInfo is referring to the new object
            personalInfo.setCandidate(candidate);
            candidate.setPersonalInfo(personalInfo);  //Now candidates personalInfo set to new object
        }
        personalInfo.setBank(bankInfoDTO.getBank());
        candidateRepo.save(candidate);
        return candidate;
    }

}
