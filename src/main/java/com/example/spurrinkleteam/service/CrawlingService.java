package com.example.spurrinkleteam.service;

import com.example.spurrinkleteam.dto.ContestDTO;
import com.example.spurrinkleteam.entity.Board.Board;
import com.example.spurrinkleteam.entity.Contest;
import com.example.spurrinkleteam.repository.CrawlingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class CrawlingService {

    @Autowired
    CrawlingRepository crawlingRepository;
    @Autowired
    MongoOperations mongoOperations;

    public void save(ContestDTO contest) {
        System.out.println("크롤링 정보 저장");
        Contest saveContest = Contest.builder()
                .title(contest.getTitle())
                .img(contest.getImg())
                .supervise(contest.getSupervise())
                .sponsor(contest.getSponsor())
                .enter(contest.getEnter())
                .target(contest.getTarget())
                .hostType(contest.getHostType())
                .scale(contest.getScale())
                .bonus(contest.getBonus())
                .benefit(contest.getBenefit())
                .body(contest.getBody())
                .startDate(contest.getStartDate())
                .endDate(contest.getEndDate())
                .link(contest.getLink())
                .build();
        crawlingRepository.save(saveContest);
    }

    public Page<Contest> getList(int page) {
        Sort sort1 = Sort.by("endDate").descending();
        Pageable pageable = PageRequest.of(page -1,10,sort1);
        Page<Contest> contestList = crawlingRepository.findAll(pageable);

//        for(int i = 0; i <10; i++) {
//            LocalDate endDate = LocalDate.parse("contestList.getContent().get(0).getEndDate()", DateTimeFormatter.ofPattern("yyyy.MM.dd"));
//            if(endDate > LocalDate.now()){
//
//            }
//        }



        return contestList;
    }

    public Page<Contest> getSearchList(String search, int page) {
        System.out.println("search: " + search);

        Sort sort1 = Sort.by("endDate").descending();
        Pageable pageable = PageRequest.of(page-1,10,sort1);

        Query query = new Query();
        query.addCriteria(where("title").regex(search));
        query.with(pageable);
        List<Contest> list = mongoOperations.find(query, Contest.class);

        Page<Contest> contestList = PageableExecutionUtils.getPage(list, pageable,
                () -> mongoOperations.count(Query.of(query).limit(-1).skip(-1), Contest.class));
        System.out.println(contestList);
        return contestList;
    }

    public Contest getContest(String title){
        System.out.println("찾으러 옴");

        return crawlingRepository.findById(title).isPresent()
                ? crawlingRepository.findById(title).get() : null;
    }

    public List<Contest> getContestList(){
        Query query = new Query();
        query.addCriteria(where("endDate").gte(new Date()));

        Pageable pageable = PageRequest.of(0,5,Sort.by("endDate").descending());
        query.with(pageable);
        List<Contest> list = mongoOperations.find(query, Contest.class);
        return list;
    }
}
