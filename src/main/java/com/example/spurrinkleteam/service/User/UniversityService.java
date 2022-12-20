package com.example.spurrinkleteam.service.User;

import com.example.spurrinkleteam.entity.University;
import com.example.spurrinkleteam.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UniversityService {

    @Autowired
    UniversityRepository universityRepository;

    public University mailcheck(String email){  //대학교 이메일이 맞는지 확인 메서드
        System.out.println("mailcheck()");
        String uniemail = email.split("@")[1];  //대학 이메일 부분만 가져옴
        System.out.println("uniemail: " + uniemail);
        University university = universityRepository.findByEmail(uniemail);  //대학 이메일 부분이 일치하는게 있는지 확인
        System.out.println("university : "  + university);
        return university ;  //대학 정보 리턴
    }
}
