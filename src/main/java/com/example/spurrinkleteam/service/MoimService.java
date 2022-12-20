package com.example.spurrinkleteam.service;

import com.example.spurrinkleteam.dto.Moim.Announcement.AnnouncementDTO;
import com.example.spurrinkleteam.dto.Moim.MoimAppliDTO;
import com.example.spurrinkleteam.dto.Moim.MoimDTO;
import com.example.spurrinkleteam.entity.Board.Board;
import com.example.spurrinkleteam.entity.ChatRoom;
import com.example.spurrinkleteam.entity.Moim.Announcement.Announcement;
import com.example.spurrinkleteam.entity.Moim.Moim;
import com.example.spurrinkleteam.entity.Moim.MoimApplicant;
import com.example.spurrinkleteam.entity.Timer.TimerTime;
import com.example.spurrinkleteam.entity.User;
import com.example.spurrinkleteam.repository.Moim.Announcement.AnnouncementRepository;
import com.example.spurrinkleteam.repository.Moim.MoimAppliRepository;
import com.example.spurrinkleteam.repository.Moim.MoimRepository;
import com.example.spurrinkleteam.repository.RoomRepository;
import com.example.spurrinkleteam.repository.Timer.TimerTimeRepository;
import com.example.spurrinkleteam.repository.UserRepository;
import com.example.spurrinkleteam.repository.Moim.Vote.VoteRepository;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class MoimService {

    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    MoimRepository moimRepository;
    @Autowired
    ChatService chatService;
    @Autowired
    TimerTimeRepository TimerTimeRepo;

    @Autowired
    MoimAppliRepository MoimAppliRepo;
    @Autowired
    private MongoOperations mongoOperations;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    VoteRepository VoteRepo;
    @Autowired
    RoomRepository RoomRepo;
    @Autowired
    MongoUpdate mongoUpdate;
    @Autowired
    AnnouncementRepository AnnouncementRepo;

    public String createMoim(MoimDTO moimDTO){
        System.out.println("모임 생성 하는 중...");
        String chatId = createMoimChat(moimDTO);    //채팅 생성
        ArrayList<String> userList = new ArrayList<String>();
        userList.add(moimDTO.getLeader());  //유저 리스트 ( 처음은 방장 한명 )

        Moim moim = Moim.builder()
                .meetName(moimDTO.getMeetName())
                .user(userList)
                .userCnt(moimDTO.getUserCnt())
                .category(moimDTO.getCategory())
                .meetIntro(moimDTO.getMeetIntro())
                .leader(moimDTO.getLeader())
                .uniName(moimDTO.getUniName())
                .applicants(new ArrayList<>())
                .photos(new ArrayList<>())
                .img(moimDTO.getImg())
                .chattingID(chatId)
//                .todoId(moimDTO.getTodoId())
//                .calId(moimDTO.getCalId())
//                .timerId(moimDTO.getTimerId())
                .nowuser(1)
                .build();
        moimRepository.save(moim);
        return moim.getMeetName();
    }
    public String createContestMoim(MoimDTO moimDTO){
        String chatId = createMoimChat(moimDTO);    //채팅 생성
        ArrayList<String> userList = new ArrayList<String>();
        userList.add(moimDTO.getLeader());  //유저 리스트 ( 처음은 방장 한명 )

        Moim moim = Moim.builder()
                .meetName(moimDTO.getMeetName())
                .contestName(moimDTO.getContestName())
                .user(userList)
                .userCnt(moimDTO.getUserCnt())
                .category(moimDTO.getCategory())
                .meetIntro(moimDTO.getMeetIntro())
                .leader(moimDTO.getLeader())
                .uniName(moimDTO.getUniName())
                .applicants(new ArrayList<>())
                .photos(new ArrayList<>())
                .img(moimDTO.getImg())
                .chattingID(chatId)
                .nowuser(1)
                .build();
        moimRepository.save(moim);
        return moim.getMeetName();
    }

    public String createMoimChat(MoimDTO moimDTO){
        System.out.println("채팅 생성중 ...");
        String room = UUID.randomUUID().toString();
        ArrayList<String> userList = new ArrayList<String>();
        userList.add(moimDTO.getLeader());  //유저 리스트 ( 처음은 방장 한명 )

        ChatRoom chat = ChatRoom.builder()
                .roomId(moimDTO.getMeetName())
                .name(moimDTO.getMeetName())
                .userId(userList)
                .whether(true)
                .build();
        return chatService.createRoom(chat);

    }

    public String checkName(String name) {
        System.out.println("중복 검사 중...");
        return moimRepository.findByMeetName(name) == null ? "T" : "F" ;
    }
    public Page<Moim> getList(int page){
        Sort sort1 = Sort.by("nowuser").descending().and(Sort.by("_id"));
        Pageable pageable = PageRequest.of(page -1,8,sort1);
        return moimRepository.findAll(pageable);
    }

    public Moim moimDetail(String name){
        Moim moim = moimRepository.findByMeetName(name);

        return moim;
    }

    public List<HashMap<String, String>> moinUserStudy(String name){
        Moim moim = moimRepository.findByMeetName(name);
        List<String> arr = moim.getUser();

        Date nowDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strNowDate = simpleDateFormat.format(nowDate);

        Sort sort1 = Sort.by("total").descending();
        List<TimerTime> timer = TimerTimeRepo.findAllByDay(strNowDate);
        List<TimerTime> list = new ArrayList<TimerTime>();
        List<String> timerUserList = new ArrayList<>();
        if(timer.size() > 0){
            Pageable pageable = PageRequest.of(0,timer.size(), sort1);

            Query query = new Query();
            query.addCriteria(where("day").is(strNowDate));
            query.with(pageable);

            // 오늘 공부한 사람 내림차순 정렬
            list = mongoOperations.find(query, TimerTime.class);
            System.out.println("=========================================");
            System.out.println("정렬 순서: " + list);
            for(int i = 0; i < list.size(); i++){
                arr.remove(list.get(i).getUserId());
                timerUserList.add(list.get(i).getUserId());
            }
            for(int i = 0; i < arr.size(); i++){
                timerUserList.add(arr.get(i));
            }
            System.out.println("공부 정렬 순서: " + timerUserList);
        }

        List<HashMap<String, String>> userTimeList = new ArrayList<HashMap<String, String>>();
        String str = "";
        for(int i = 0; i < timerUserList.size(); i++){
            HashMap<String, String> userTime = new HashMap<>();
            TimerTime time = TimerTimeRepo.findByUserIdAndDay(timerUserList.get(i), strNowDate);
            System.out.println("time: " + time);
            if(time != null){
                str += timerUserList.get(i) + " ";
                int index = list.indexOf(time);
                int count = time.getTotal();
                int checkMinutes = (int) Math.floor(count / 60);
                int hours = (int) Math.floor(count / 3600);
                int minutes = checkMinutes % 60;
                int seconds = count % 60;

                String strHour = String.valueOf(hours);
                String strMinutes = String.valueOf(minutes);
                String strSeconds = String.valueOf(seconds);

                String ho = strHour.length() > 1 ? strHour : "0" + strHour;
                String min = strMinutes.length() > 1 ? strMinutes  : "0" + strMinutes;
                String sec = strSeconds.length() > 1 ? strSeconds  : "0" + strSeconds;

                ho = strTran(ho, "H");
                min = strTran(min, "M");
                sec = strTran(sec, "S");

                userTime.put(timerUserList.get(i), ho + min + sec);
                userTimeList.add(userTime);
            }else{
                str += timerUserList.get(i) + " ";
                userTime.put(timerUserList.get(i), "00H:00M:00S");
                userTimeList.add(userTime);}
        }
        if(timerUserList.size() == 0){
            for(int i = 0; i < arr.size(); i++){
                HashMap<String, String> userTime = new HashMap<>();
                str += arr.get(i) + " ";
                userTime.put(arr.get(i), "00H:00M:00S");
                userTimeList.add(userTime);
            }
        }

        HashMap<String, String> userList = new HashMap<>();

        userList.put("userList", str);
        userTimeList.add(userList);

        System.out.println("userTimeList: " + userTimeList);
        System.out.println("=========================================");
        return userTimeList;
    }
    public String strTran(String str, String str2){
        if(str.equals("00")){
            if(!str2.equals("S")){
                str = "00" + str2 +":";
            }else{str = "00" + str2;}
        }else{
            if(!str2.equals("S")){
                str = str + str2 +":";
            }else{str = str + str2;}
        }
        return str;
    }

    public String applicant(MoimAppliDTO moimAppliDTO){
        System.out.println(moimAppliDTO.getId() + "    date: " + moimAppliDTO.getDate());
        MoimApplicant moimApp = MoimApplicant.builder()
                .meetName(moimAppliDTO.getMeetName())
                .userId(moimAppliDTO.getUserId())
                .content(moimAppliDTO.getContent())
                .date(moimAppliDTO.getDate())
                .build();
        MoimAppliRepo.save(moimApp);

        Moim moim = moimRepository.findByMeetName(moimAppliDTO.getMeetName());

        List<String> userList = moim.getApplicants();
        userList.add(moimApp.getUserId());
        System.out.println(userList);


        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(moim.getMeetName()));
        //업데이트 할 항목 정의
        Update update = new Update();
        update.set("applicants", userList);

        mongoTemplate.updateFirst(query, update, "Moim");

        return "success";
    }
    public List<MoimApplicant> applicantUserListCheck(String meetName){
        return MoimAppliRepo.findByMeetName(meetName);
    }
    public MoimApplicant applicantUserCheck(String meetName, String userId){
        return MoimAppliRepo.findByUserIdAndMeetName(userId, meetName);
    }

    public String applicantUserSubmit(String meetName,String id){
        Optional<MoimApplicant> app = MoimAppliRepo.findById(id);
        MoimAppliRepo.deleteById(id);
        Query query = new Query();

        Moim moim = moimRepository.findByMeetName(meetName);
        List<String> userList = moim.getApplicants();
        List<String> orUserList = moim.getUser();
        query.addCriteria(Criteria.where("_id").is(meetName));
        //업데이트 할 항목 정의
        Update update = new Update();
        userList.remove(app.get().getUserId());
        update.set("applicants", userList);
        update.set("nowuser", moim.getNowuser()+1);

        orUserList.add(app.get().getUserId());
        update.set("user", orUserList);

        mongoTemplate.updateFirst(query, update, "Moim");
        return "success";
    }
    public Page<Moim> getSearchList(int page, String search){
        System.out.println("search: " + search);


        Sort sort1 = Sort.by("nowuser").descending().and(Sort.by("_id"));
        Pageable pageable = PageRequest.of(page -1,8,sort1);

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").regex(search));
        query.with(pageable);
        List<Moim> list = mongoOperations.find(query, Moim.class);

        Page<Moim> moimList = PageableExecutionUtils.getPage(list, pageable,
                () -> mongoOperations.count(Query.of(query).limit(-1).skip(-1), Moim.class));
        System.out.println(moimList);

        return moimList;
    }
    public String apllicantReject(ObjectNode data){
        String appliID = String.valueOf(data.get("appliID")).replaceAll("\\\"","");
        String meetName = String.valueOf(data.get("meetName")).replaceAll("\\\"","");
        String userID = String.valueOf(data.get("userID")).replaceAll("\\\"","");

        Moim moim = moimRepository.findByMeetName(meetName);
        List<String> userList = moim.getApplicants();

        // 가입거절 당한 유저 모임 신청자명단에서 제거
        Update update = new Update();
        userList.remove(userID);
        update.set("applicants", userList);

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(meetName));
        try{
            mongoTemplate.updateFirst(query, update, "Moim");

            // 해당 유저 신청서 삭제
            MoimAppliRepo.deleteById(appliID);
            return "success";
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            return "fail";
        }
    }
    public Page<Moim> userMoim(String id, int page){
        Sort sort1 = Sort.by("nowuser").descending().and(Sort.by("_id"));
        Pageable pageable = PageRequest.of(page -1,8,sort1);
        return moimRepository.findAllByUserIn(id, pageable);
    }
    public String moimUpdate(String meetName, ObjectNode data){
        String userCnt = String.valueOf(data.get("userCnt")).replaceAll("\\\"","");
        String category = String.valueOf(data.get("category")).replaceAll("\\\"","");
        String meetIntro = String.valueOf(data.get("meetIntro")).replaceAll("\\\"","");

        Query query = mongoUpdate.mongoQuery("_id", meetName);
        Update update = new Update();
        update.set("userCnt", userCnt);
        update.set("category", category);
        update.set("meetIntro", meetIntro);

        Moim moim = moimRepository.findByMeetName(meetName);
        if(moim.getCategory().equals("공모전") && !category.equals("공모전")){
            update.set("contestName", "");
        }
        if(category.equals("공모전")){
            String contestName = String.valueOf(data.get("contestName")).replaceAll("\\\"","");
            update.set("contestName", contestName);
        }
        return mongoUpdate.mongoUpdate(query, update, "Moim");
    }
    public Optional<User> moimUser(String user){
        return userRepository.findById(user);
    }

    public String userOut(String meetName, ObjectNode data){
        String userID = String.valueOf(data.get("userId")).replaceAll("\\\"","");
        Moim moim = moimRepository.findByMeetName(meetName);
        List<String> userList = moim.getUser();


        Query query = mongoUpdate.mongoQuery("_id", meetName);

        //
        Update update = new Update();
        userList.remove(userID);
        update.set("user", userList);
        update.set("nowuser", moim.getNowuser()-1);
        return mongoUpdate.mongoUpdate(query, update, "Moim");
    }
    public String userDelegate(String meetName, ObjectNode data){
        String user = String.valueOf(data.get("delegate")).replaceAll("\\\"","");

        Query query = mongoUpdate.mongoQuery("_id", meetName);

        // 모임 리더 변경
        Update update = new Update();
        update.set("leader", user);
        return mongoUpdate.mongoUpdate(query, update, "Moim");
    }
    public String deleteMoim(String meetName){
        Optional<Moim> moim = moimRepository.findById(meetName);
        // 신청명단 삭제
        for(int i = 0; i < moim.get().getApplicants().size(); i++){
            MoimAppliRepo.deleteById(moim.get().getApplicants().get(i));
        }
        // 채팅 삭제
        RoomRepo.deleteById(moim.get().getChattingID());
        // 투표 삭제
        VoteRepo.deleteAllByMeetName(meetName);
        // 모임 삭제
        moimRepository.deleteById(meetName);

        return "success";
    }
    public String moimOut(String meetName, ObjectNode data){
        // 탈퇴할 사용자 아이디
        String userOut = String.valueOf(data.get("id")).replaceAll("\\\"","");
        Optional<Moim> moim = moimRepository.findById(meetName);
        List<String> user = moim.get().getUser();
        if(user.contains(userOut)){
            user.remove(userOut);

            Query query = mongoUpdate.mongoQuery("_id", meetName);
            Update update = new Update();
            update.set("user", user);
            update.set("nowuser", moim.get().getNowuser()-1);
            return mongoUpdate.mongoUpdate(query, update, "Moim");

        }else{
            return "fail";  // 이미 강퇴 당했거나 탈퇴했거나
        }
    }
    public String moimLeader(String meetName){
        Optional<Moim> moim = moimRepository.findById(meetName);
        return moim.get().getLeader();
    }
    public String writeAnn(AnnouncementDTO ann){
        Announcement announcement = Announcement.builder()
                .meetName(ann.getMeetName())
                .title(ann.getTitle())
                .userId(ann.getUserId())
                .content(ann.getContent())
                .date(ann.getDate())
                .build();
        try{
            AnnouncementRepo.save(announcement);
            return announcement.getId();
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            return "fail";
        }
    }
    public Optional<Announcement> moimAnnouncement(String annId){
        return AnnouncementRepo.findById(annId);
    }

    public List<Announcement> moimAnnouncementList(String meetName){
        return AnnouncementRepo.findAllByMeetName(meetName);
    }
    public Optional<Announcement> moimAnnouncementUpp(String annId){
        return AnnouncementRepo.findById(annId);
    }
    public String moimUpdateAnn(String annId, ObjectNode ann){
        // 공지사항 내용 수정
        String content = String.valueOf(ann.get("content")).replaceAll("\\\"","");
        String title = String.valueOf(ann.get("title")).replaceAll("\\\"","");

        Query query = mongoUpdate.mongoQuery("_id", annId);
        Update update = new Update();
        update.set("title", title);
        update.set("content", content);
        return mongoUpdate.mongoUpdate(query, update, "announcements");
    }
}