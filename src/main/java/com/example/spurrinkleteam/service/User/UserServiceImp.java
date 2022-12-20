package com.example.spurrinkleteam.service.User;

import com.example.spurrinkleteam.dto.UserDTO;
import com.example.spurrinkleteam.entity.User;
import com.example.spurrinkleteam.repository.MongoDBRepository;
import com.example.spurrinkleteam.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    MongoDBRepository mongoDBRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    MongoTemplate mongoTemplate;


    @Override
    public User login(UserDTO user) {
        System.out.println(user.getId() + " , " + user.getPw());
        if (userRepository.findById(user.getId()).isPresent()) {  //아이디가 일치한 회원 검사
            User userinfo = userRepository.findById(user.getId()).get();    //아이디가 일치한 회원 받기
            if (passwordEncoder.matches(user.getPw(), userinfo.getPw())) {    //암호화 비번이 일치한지 확인
                return userinfo;    // 유저 정보 리턴
            }
        }
        return null;
    }

    @Override
    public User userMailCheck(String email) {  //현재 이메일을 가진 유저가 있는지 확인 메서드
        System.out.println("userMailCheck()");
        System.out.println("userEmail : " + email);
        User user = userRepository.findByEmail(email);  //이메일이 겹치는 인원 확인
        System.out.println(user);
        return user;    //검색된 유저 정보 반환
    }

    @Override
    public User userIdCheck(String id) { //유저 아이디 중복 확인
        System.out.println("id : " + id);
        //isPresent로 값이 있는지 확인 해야함
        //id값은 없으면 null이아니라 터짐
        return userRepository.findById(id).isPresent() ? userRepository.findById(id).get() : null;
    }

    @Override
    public User userNicknameCheck(String nickname) {
        System.out.println(nickname);
        User user = userRepository.findByNickname(nickname);    //닉네임 일치한 회원 가져오기
        return user;
    }

    @Override
    public void userjoin(UserDTO userinfo) {
        System.out.println("user : " + userinfo);
        System.out.println(passwordEncoder.encode(userinfo.getPw()));
        User user = User.builder()
                .id(userinfo.getId())
                .pw(passwordEncoder.encode(userinfo.getPw()))   //비번 암호화 후 저장
                .name(userinfo.getName())
                .email(userinfo.getEmail())
                .nickname(userinfo.getNickname())
                .tel(userinfo.getTel())
                .gender(userinfo.getGender())
                .uniName(userinfo.getUniName())
                .img(userinfo.getImg())
                .subject(new String[]{"과목1"})
                .build();
        mongoDBRepository.save(user);   //회원 정보 저장
    }

    @Override
    public String userUni(String id) {
        Optional<User> user = userRepository.findById(id);
        System.out.println("uni: " + user.get().getUniName());
        return user.get().getUniName();
    }

    public User getProfile(UserDTO user) {
        if (userRepository.findById(user.getId()).isPresent()) {  //아이디가 일치한 회원 검사
            User userinfo = userRepository.findById(user.getId()).get();    //아이디가 일치한 회원 받기
            return userinfo;    // 유저 정보 리턴
        }
            return null;
    }

//    @Override
//    public String userImg(MultipartFile img, String id, HttpServletRequest req) {
//        try{
//            ArrayList<String> files = new ArrayList<String>();
//            File folder = new File("src/main/frontend/public/img/profile");
//            if(!folder.exists()){
//                folder.mkdirs();
//            }
//
//            File destination = new File(id);    // 유저당 하나의 프로필 사진만 가지기 때문에 파일명을 아이디로 지정
//            img.transferTo(destination);
//            files.add(id);
//
//            Optional<User> user = userRepository.findById(id);
//
//            ObjectId _id = new ObjectId(id);
//            Query query = new Query();
//            query.addCriteria(Criteria.where("_id").is(_id));
//            //업데이트 할 항목 정의
//            Update update = new Update();
//            update.set("img", files);
//
//            mongoTemplate.updateFirst(query, update, "users");
//
//            return "success";
//        }catch(Exception e){
//            System.out.println("Error: " + e.getMessage());
//            return "fail";
//        }
//}


    @Override
    public User updateImg(UserDTO user){
        System.out.println("이미지 수정");

        Criteria criteria = new Criteria("_id");
        criteria.is(user.getId());
        Query query = new Query(criteria);

        Update update = new Update();
        update.set("img", user.getImg());

        mongoTemplate.updateFirst(query, update, "users");

        return userIdCheck(user.getId());
    }

    @Override
    public User updateName(UserDTO user){
        System.out.println("이름과 전번 수정 : " + user.getName());

        Criteria criteria = new Criteria("_id");
        criteria.is(user.getId());
        Query query = new Query(criteria);

        Update update = new Update();
        update.set("name", user.getName());
        update.set("tel", user.getTel());

        mongoTemplate.updateFirst(query, update, "users");

        return userIdCheck(user.getId());
    }

    @Override
    public User updatePw(UserDTO user){
        System.out.println("비밀번호 수정 : " + user.getPw());

        Criteria criteria = new Criteria("_id");
        criteria.is(user.getId());
        Query query = new Query(criteria);

        Update update = new Update();
        update.set("pw", passwordEncoder.encode(user.getPw()));

        mongoTemplate.updateFirst(query, update, "users");

        return userIdCheck(user.getId());
    }

    public void resetPw(String email, String pw){
        System.out.println("임시 비밀번호");
        Criteria criteria = new Criteria("email");
        criteria.is(email);
        Query query = new Query(criteria);

        Update update = new Update();
        update.set("pw", passwordEncoder.encode(pw));

        mongoTemplate.updateFirst(query, update, "users");
    }

    @Override
    public User updateNick(UserDTO user){
        System.out.println("닉네임 수정 : " + user.getNickname());

        Criteria criteria = new Criteria("_id");
        criteria.is(user.getId());
        Query query = new Query(criteria);

        Update update = new Update();
        update.set("nickname", user.getNickname());

        mongoTemplate.updateFirst(query, update, "users");

        return userIdCheck(user.getId());
    }

    @Override
    public User updateGender(UserDTO user){
        System.out.println("성별 수정 : " + user.getGender());
        Criteria criteria = new Criteria("_id");
        criteria.is(user.getId());
        Query query = new Query(criteria);
        Update update = new Update();
        update.set("gender", user.getGender());
        mongoTemplate.updateFirst(query, update, "users");
        return userIdCheck(user.getId());
    }

    public void delete(String id){
        Criteria criteria = new Criteria("_id");
        criteria.is(id);
        Query query = new Query(criteria);
        mongoTemplate.remove(query, "users");
    }
    public Optional<User> getUserInfo(String id){
        return userRepository.findById(id);
    }
    public List<User> getUserListInfo(ObjectNode userList) {
        System.out.println("날아온거: " + userList);
        JsonNode user = userList.get("userList");
        System.out.println("날아온거user: " + user);
        List<User> userArr = new ArrayList<>();
        for(int i = 0; i < user.size(); i++) {
            if(i != user.size()-1){
                String id = String.valueOf(user.get(i)).replaceAll("\\\"","");
                Optional<User> u = userRepository.findById(id);
                userArr.add(u.get());
            }
        }
        return userArr;
    }

    public String idAuth(String tel){
        User user = userRepository.findByTel(tel);

        return user.getId();
    }

    public String EmailAuth(String email){
        User user = userRepository.findByEmail(email);
        return user.getId();
    }

}
