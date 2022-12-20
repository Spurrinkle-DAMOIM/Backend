package com.example.spurrinkleteam.controller;

import com.example.spurrinkleteam.dto.UserDTO;
import com.example.spurrinkleteam.entity.University;
import com.example.spurrinkleteam.entity.User;
import com.example.spurrinkleteam.service.Timer.TimerService;
import com.example.spurrinkleteam.service.TodoService;
import com.example.spurrinkleteam.service.User.ApiService;
import com.example.spurrinkleteam.service.User.MailService;
import com.example.spurrinkleteam.service.User.UniversityService;
import com.example.spurrinkleteam.service.User.UserServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    @Autowired
    MailService mailService;
    @Autowired
    UserServiceImp userService;
    @Autowired
    UniversityService universityService;
    @Autowired
    ApiService apiService;
    @Autowired
    TimerService timerService;
    @Autowired
    TodoService todoService;

    @PostMapping("/login")
    public User login(@RequestBody UserDTO user){ //로그인
        return userService.login(user);
    }

    @GetMapping("/idDupCheck")  //아이디 중복 확인
    public String idCheck(@RequestParam String id) {    //아이디 중복 확인
        return userService.userIdCheck(id) != null ? "F" : "T";
    }

    @GetMapping("/nickDupCheck")
    public String nicknameCheck(@RequestParam String nickname) {    //닉네임 중복 확인
        return userService.userNicknameCheck(nickname) != null ? "F" : "T";
    }

    @GetMapping("/emailCheck")
    public Map<String, String> emailCheck(@RequestParam String email) throws MessagingException, IOException {    //이메일 인증
        Map<String, String> map = new HashMap<String, String>();
        User user = userService.userMailCheck(email);   //중복된 이메일인지 확인
        if(user == null) {  //중복되지 않았다면
            University university = universityService.mailcheck(email);   // 대학 정보 받기
            if(university != null) {    //존재하는 대학이라면
                String authNum = mailService.createAuthNum();//인증번호 발생
                mailService.send("DAMOIM 인증번호 입니다.", email, "mail", authNum);   //인증번호 메일 전송
                map.put("status", "T"); //map에 정보 저장
                map.put("university", university.getName());
                map.put("authNum", authNum);
                return map;
            }else{
                map.put("status", "UF"); //map에 실패한 정보 저장
            }
        }else{
            map.put("status", "F"); //map에 실패한 정보 저장
        }
        return map;
    }
    @PostMapping("/join")
    public String join(@RequestBody UserDTO user){  //회원 정보 저장
        user.setImg("https://spurrinkle.s3.ap-northeast-2.amazonaws.com/default_user_img.png"); //기본 이미지
        userService.userjoin(user); //회원 정보 저장
        timerService.createTimer(user.getId());
        todoService.makeTodo(user.getId());
        return "T";
    }
    
    @GetMapping ("/{id}/uniName")
    public String uniName(@PathVariable String id){  //회원 정보 저장
        return userService.userUni(id); // 회원의 대학교명
    }

    @PostMapping("/profile")
    public User getProfile(@RequestBody UserDTO user){ return userService.getProfile(user); }

//    @PostMapping(value = "/{id}/img")
//    public String userImg(@RequestParam("img") MultipartFile img, @PathVariable String id, HttpServletRequest req){
//        return userService.userImg(img, id, req);
//    }

    @PostMapping(value = "/update/img")
    public User userImg(@RequestBody UserDTO user){
        user.setImg("https://spurrinkle.s3.ap-northeast-2.amazonaws.com/"+user.getImg());
        return userService.updateImg(user);
    }

    @PostMapping("/update/pw")
    public User updatePw(@RequestBody UserDTO user){
        System.out.println("비밀번호 수정");
        return userService.updatePw(user);
    }
    @PostMapping("/update/name")
    public User updateName(@RequestBody UserDTO user){
        System.out.println("이름과 전화번호 수정");
        return userService.updateName(user);
    }
    @PostMapping("/update/nickname")
    public User updateNick(@RequestBody UserDTO user){

        System.out.println("닉네임 수정" + user.getNickname());
        return userService.updateNick(user);
    }

    @PostMapping("/update/gender")
    public User updateGender(@RequestBody UserDTO user){
        System.out.println("성별 수정");
        return userService.updateGender(user);
    }

    @PostMapping("/delete")
    public void delete(@RequestBody UserDTO user){
        System.out.println("삭제 : "+ user.getId());
        userService.delete(user.getId());
    }

    @GetMapping("/{user}/userInfo")
    public Optional<User> getUserInfo(@PathVariable String user) {
        return userService.getUserInfo(user);
    }

    @GetMapping("/test")
    public String test(){
        return "테스트 성공이요~";
    }

    @PostMapping("/userListInfo")
    public List<User> getUserListInfo(@RequestBody ObjectNode userList) {
        return userService.getUserListInfo(userList);
    }

    @PostMapping("/pwFind")
    public String pwFind(@RequestBody UserDTO userEmail) throws MessagingException, IOException {
        Map<String, String> map = new HashMap<String, String>();
        System.out.println("들어옴...");
        User user = userService.userMailCheck(userEmail.getEmail());   //이메일이 존재 하는지 확인
        if(user != null) {  //존재 한다면
            System.out.println("존재함...");
            String authNum = mailService.createPw();//인증번호 발생
            mailService.send("DAMOIM 임시비밀번호 발급 안내 입니다.", userEmail.getEmail(), "pw", authNum);   //인증번호 메일 전송
            userService.resetPw(userEmail.getEmail(), authNum);
            return "T";
        }
        System.out.println("없음...");
        return "T";
    }

    @PostMapping("/check/info")
    public String checkInfo(@RequestBody ObjectNode node) throws IOException, ParseException {
        System.out.println("checkInfo");
        ObjectMapper mapper = new ObjectMapper();
        String imp_uid = node.get("imp_uid").asText();
        System.out.println(imp_uid);
        Map<String, String> map = apiService.CertificationCheck(apiService.getToken(),imp_uid);

        return userService.idAuth(map.get("phone"));
    }

    @PostMapping("/idFind")
    public String idFind(@RequestBody UserDTO userEmail) throws MessagingException, IOException {
        Map<String, String> map = new HashMap<String, String>();
        System.out.println("들어옴...");
        User user = userService.userMailCheck(userEmail.getEmail());   //이메일이 존재 하는지 확인
        if(user != null) {  //존재 한다면
            System.out.println("존재함...");
            mailService.send("DAMOIM 아이디 찾기 결과입니다.", userEmail.getEmail(), "id", userService.EmailAuth(userEmail.getEmail()));   //인증번호 메일 전송
            return "T";
        }
        System.out.println("없음...");
        return "T";
    }

}
