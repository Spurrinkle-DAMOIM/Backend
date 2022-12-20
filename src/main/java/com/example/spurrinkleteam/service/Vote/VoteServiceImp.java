package com.example.spurrinkleteam.service.Vote;

import com.example.spurrinkleteam.dto.Moim.Vote.VoteDTO;
import com.example.spurrinkleteam.entity.Moim.Vote.Vote;
import com.example.spurrinkleteam.repository.Moim.Vote.VoteRepository;
import com.example.spurrinkleteam.service.MongoUpdate;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.bson.types.ObjectId;
import org.json.simple.JSONObject;
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

import java.util.ArrayList;
import java.util.Optional;

@Service
public class VoteServiceImp implements VoteService {
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    VoteRepository VoteRepo;
    @Autowired
    MongoUpdate mongoUpdate;
    @Override
    public String saveVote(VoteDTO vote) {

        ArrayList<JSONObject> onList = new ArrayList<>();

        for (String name : vote.getList()){
            JSONObject obj = new JSONObject();
            obj.put("vote", name);
            obj.put("users", new ArrayList<>());
            onList.add(obj);
        }

        Vote saveVote = Vote.builder()
                .meetName(vote.getMeetName())
                .host(vote.getHost())
                .title(vote.getTitle())
                .list(vote.getList())
                .onList(onList)
                .date(vote.getDate())
                .onOff(vote.getOnOff())
                .build();
        Vote v = VoteRepo.save(saveVote);
        System.out.println("투표 저장: " + v);
        return v.getId();
    }


    @Override
    public Page<Vote> getVoteList(String meet, int page) {
        Sort sort1 = Sort.by("date").descending();
        Pageable pageable = PageRequest.of(page -1,10,sort1);
        return VoteRepo.findAllByMeetName(meet, pageable);
    }

    @Override
    public String userVote(String id, ObjectNode vote) {
        String meetName = String.valueOf(vote.get("meetName")).replaceAll("\\\"","");
        String user = String.valueOf(vote.get("user")).replaceAll("\\\"","");
        String voteName = String.valueOf(vote.get("vote")).replaceAll("\\\"","");

        Optional<Vote> voteDB = VoteRepo.findById(id);

        ArrayList<JSONObject> onList = voteDB.get().getOnList();

        for(int i=0; i< onList.size(); i++){
            System.out.println("투표한 이름 : " + voteName);
            System.out.println(onList.get(i).size());
            System.out.println(onList.get(i));
            System.out.println(onList.get(i).get("vote"));
            if(onList.get(i).get("vote").equals(voteName)){
                System.out.println(onList.get(i).get("users"));
                ArrayList list = (ArrayList) onList.get(i).get("users");
                list.add(user);
                System.out.println(list);
                onList.get(i).put("users", list);
            }
        }

//        for(JSONObject obj2 : onList){
//            if(obj2.size() > 0) {
//                System.out.println("투표한 이름 : " + voteName);
//                System.out.println(obj2);
//                System.out.println();
//                System.out.println(obj2.get(voteName));
//                if (obj2.get("vote").equals(voteName)) {
//                    System.out.println(obj2.get("users"));
//                    ;
//                    ArrayList list = (ArrayList) obj2.get("users");
//                    list.add(user);
//                    System.out.println(list);
//                }
//            }
//        }


        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));

        Update update = new Update();
        update.set("onList", onList);
        try{
            mongoTemplate.updateFirst(query, update, "vote");
            return "success";
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            return "fail";
        }
    }
    @Override
    public String voteOff(ObjectNode vote){
        String voteID = String.valueOf(vote.get("voteID")).replaceAll("\\\"","");
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(voteID)));

        Update update = new Update();
        update.set("onOff", true);
        mongoTemplate.updateFirst(query, update, "vote");
        return "success";
    }

    @Override
    public Optional<Vote> getVote(String voteId) {
        return VoteRepo.findById(voteId);
    }

    @Override
    public String voteDelete(ObjectNode vote){
        String voteID = String.valueOf(vote.get("voteID")).replaceAll("\\\"","");
        try{
            VoteRepo.deleteById(voteID);
            return "success";
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            return "fail";
        }
    }
}
