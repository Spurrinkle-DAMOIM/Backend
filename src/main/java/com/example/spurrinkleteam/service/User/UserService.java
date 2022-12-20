package com.example.spurrinkleteam.service.User;

import com.example.spurrinkleteam.dto.UserDTO;
import com.example.spurrinkleteam.entity.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    public User login(UserDTO user);
    public User userMailCheck(String email);
    public User userIdCheck(String id);
    public User userNicknameCheck(String nickname);
    public void userjoin(UserDTO userinfo);
    abstract String userUni(String id);
    public User getProfile(UserDTO user);
//    abstract String userImg(MultipartFile img, String id, HttpServletRequest req);
    abstract User updateImg(UserDTO user);
    abstract User updateName(UserDTO user);
    abstract User updatePw(UserDTO user);
    abstract User updateNick(UserDTO user);
    abstract User updateGender(UserDTO user);
    abstract void delete(String id);
}
