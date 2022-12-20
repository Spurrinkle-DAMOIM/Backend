package com.example.spurrinkleteam.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import com.example.spurrinkleteam.service.User.ApiService;
import com.example.spurrinkleteam.service.User.MailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.util.Map;
@RestController
@AllArgsConstructor
public class HelloController {
    @Autowired
    MailService mailService;
    //자동 주입 받는 클래스를 new생성시 자동 주입 하지 않음
    //자동 주입이 하나라도 있는 클래스는 똑같이 자동주입 받아야 함
    @Autowired
    ApiService apiService;

    @GetMapping("/api/hello")
    public String test(){
        return "Hello";
    }

    @GetMapping("/sendmail")
    public void sendMail() throws Exception{
        System.out.println("sendMail()");
        mailService.send("DAMOIM 인증번호 입니다.", "yon593737@naver.com", "mail", "123456");
    }

    @PostMapping("/check/info")
    public Map<String, String> checkInfo(@RequestBody ObjectNode node) throws IOException, ParseException {
        System.out.println("checkInfo");
        ObjectMapper mapper = new ObjectMapper();
        String imp_uid = node.get("imp_uid").asText();
        System.out.println(imp_uid);
        Map<String, String> map = apiService.CertificationCheck(apiService.getToken(),imp_uid);
        return map;
    }
}
