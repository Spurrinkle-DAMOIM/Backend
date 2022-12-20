package com.example.spurrinkleteam.controller;

import com.example.spurrinkleteam.crawling.Crawling;
import com.example.spurrinkleteam.entity.Contest;
import com.example.spurrinkleteam.service.CrawlingService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/crawling")
public class CrawlingController {

    @Autowired
    Crawling crawling;

    @Autowired
    CrawlingService crawlingService;

    @GetMapping("/start")
    public void start(){
        System.out.println("크롤링 시작버튼");
        try {
            Thread t = crawling;
            t.start();
        }catch (Exception e){
            System.out.println("크롤링 오류");
            e.printStackTrace();
        }
        System.out.println("크롤링만 돌아가는 중...");
    }

    @GetMapping("/stop")
    public void stop(){

    }

    @GetMapping("/wakeUp")
    public void wakeUp(){

    }

    @GetMapping("/sleep")
    public void sleep(){

    }

    @GetMapping("/getList/{page}")
    public Page<Contest> getList(@PathVariable int page) {
        System.out.println("페이지 요청 : " + page);
        return crawlingService.getList(page);
    }

    @GetMapping("/getSearchList/{page}")
    public Page<Contest> getSearchList(@RequestParam String search, @PathVariable int page){
        System.out.println(" 검색된 페이지 요청 : " + search +" "+ page);
        return crawlingService.getSearchList(search, page);
    }

    @GetMapping("/getContest/{title}")
    public Contest getContest(@PathVariable String title){
        System.out.println("하나 받으러옴" + title);
        return crawlingService.getContest(title);
    }

    @GetMapping("/contestList")
    public List<Contest> getContestList(){
        return crawlingService.getContestList();
    }
}
