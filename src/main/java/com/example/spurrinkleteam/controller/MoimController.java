package com.example.spurrinkleteam.controller;

import com.example.spurrinkleteam.dto.Moim.Announcement.AnnouncementDTO;
import com.example.spurrinkleteam.dto.Moim.MoimAppliDTO;
import com.example.spurrinkleteam.dto.Moim.MoimDTO;
import com.example.spurrinkleteam.entity.Moim.Announcement.Announcement;
import com.example.spurrinkleteam.entity.Moim.Moim;
import com.example.spurrinkleteam.entity.Moim.MoimApplicant;
import com.example.spurrinkleteam.entity.User;
import com.example.spurrinkleteam.service.MoimService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Moim")
public class MoimController {

    @Autowired
    MoimService moimService;

    @PostMapping("/createMoim")
    public String createMoim(@RequestBody MoimDTO moim){
        System.out.println("moim생성");
        return moimService.createMoim(moim);
    }
    @PostMapping("/createContestMoim")
    public String createContestMoim(@RequestBody MoimDTO moim){
        System.out.println("moim생성");
        return moimService.createContestMoim(moim);
    }

    @GetMapping("/checkName")
    public String checkName(@RequestParam(value = "meetName") String meetName){
        System.out.println("모임 이름 중복검사");
        return moimService.checkName(meetName);
    }

    @GetMapping("/MoimList")
    public Page<Moim> moimList(@RequestParam int page){
        System.out.println("모임 리스트 생성");
        return moimService.getList(page);
    }


    @GetMapping("/{name}/moimDetail")
    public Moim moimDetail(@PathVariable String name){
        return moimService.moimDetail(name);
    }

    @GetMapping("/MoimList/search")
    public Page<Moim> moimListSearch(@RequestParam int page, @RequestParam String search){
        System.out.println("검색 모임 리스트 생성");
        return moimService.getSearchList(page, search);
    }


    @GetMapping("/{name}/moimUserStudy")
    public List<HashMap<String, String>> moinUserStudy(@PathVariable String name){
        return moimService.moinUserStudy(name);
    }

    @PostMapping("/apllicant")
    public void apllicant(@RequestBody MoimAppliDTO moimAppli){
        moimService.applicant(moimAppli);
    }
    @GetMapping("/applicant/{meetName}/userCheck")
    public List<MoimApplicant> applicantUserListCheck(@PathVariable String meetName){
        return moimService.applicantUserListCheck(meetName);
    }
    @GetMapping("/applicant/{meetName}/{userId}")
    public MoimApplicant applicantUserCheck(@PathVariable String meetName, @PathVariable String userId){
        return moimService.applicantUserCheck(meetName, userId);
    }
    @GetMapping("/applicantSubmit/{meetName}/{id}")
    public String applicantUserSubmit(@PathVariable String meetName, @PathVariable String id){
        return moimService.applicantUserSubmit(meetName, id);
    }
    @PostMapping("/applicant/reject")
    public String apllicantReject(@RequestBody ObjectNode data){
        return moimService.apllicantReject(data);
    }
    @GetMapping("/{id}/moim")
    public Page<Moim> userMoim(@PathVariable String id, @RequestParam int page){    //나의 모임 리스트
        return moimService.userMoim(id, page);
    }
    @PostMapping("/{meetName}/moim/update")
    public String moimUpdate(@PathVariable String meetName, @RequestBody ObjectNode data){
        return moimService.moimUpdate(meetName, data);
    }
    @GetMapping("/{user}/user")
    public Optional<User> moimUser(@PathVariable String user){
        return moimService.moimUser(user);
    }
    @PostMapping("/{meetName}/userOut")
    public String userOut(@PathVariable String meetName, @RequestBody ObjectNode data){
        // 강퇴
        return moimService.userOut(meetName, data);
    }
    @PostMapping("/{meetName}/delegate")
    public String userDelegate(@PathVariable String meetName, @RequestBody ObjectNode data){
        return moimService.userDelegate(meetName, data);
    }
    @GetMapping("/{meetName}/delete")
    public String deleteMoim(@PathVariable String meetName){
        return moimService.deleteMoim(meetName);
    }
    @PostMapping("/{meetName}/moimOut")
    public String moimOut(@PathVariable String meetName, @RequestBody ObjectNode data){
        // 모임탈퇴
        return moimService.moimOut(meetName, data);
    }
    @GetMapping("/{meetName}/leader")
    public String moimLeader(@PathVariable String meetName){
        return moimService.moimLeader(meetName);
    }
    @PostMapping("/writeAnn")
    public String writeAnn(@RequestBody AnnouncementDTO ann){
        return moimService.writeAnn(ann);
    }
    @GetMapping("/announcement/{annId}")
    public Optional<Announcement> moimAnnouncement(@PathVariable String annId){
        return moimService.moimAnnouncement(annId);
    }
    @GetMapping("/announcement/{meetName}/list")
    public List<Announcement> moimAnnouncementList(@PathVariable String meetName){
        return moimService.moimAnnouncementList(meetName);
    }
    @GetMapping("/announcement/{annId}/update")
    public Optional<Announcement> moimAnnouncementUpp(@PathVariable String annId){
        return moimService.moimAnnouncementUpp(annId);
    }
    @PostMapping("/announcement/{annId}/updateData")
    public String moimUpdateAnn(@PathVariable String annId, @RequestBody ObjectNode ann){
        return moimService.moimUpdateAnn(annId, ann);
    }

}