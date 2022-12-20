package com.example.spurrinkleteam.crawling;

import com.example.spurrinkleteam.dto.ContestDTO;
import com.example.spurrinkleteam.entity.Contest;
import com.example.spurrinkleteam.repository.CrawlingRepository;
import com.example.spurrinkleteam.service.CrawlingService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

@Component
@Service
public class Crawling extends Thread{

    @Autowired
    CrawlingService crawlingService;

    public void run() {
        try {
//            while (true) {
                System.out.println("크롤링을 시작 합니다!!");
                ContestDTO contest = new ContestDTO();
                String TURL = "https://campusmon.jobkorea.co.kr";
                String bTitleURL = "https://campusmon.jobkorea.co.kr/Contest/List?_Page=";
    //
                for(int k=0; k<5; k++) {
                    String titleURL = bTitleURL + k;
                    Document titleDoc;
                    titleDoc = Jsoup.connect(titleURL)
                            .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
                            .get(); // 원하는 url에서 전체 구조를 받아온다
                    Elements sURL = titleDoc.select("table.cTb tbody>tr>td>p>a");
                    for (int i = 0; i < sURL.size(); i++) {
                        System.out.println(sURL.get(i).attr("href"));
                        Document doc;
                        Document innerDoc;
                        doc = Jsoup.connect(TURL + sURL.get(i).attr("href"))
                                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
                                .get(); // 원하는 url에서 전체 구조를 받아온다
                        Elements title = doc.select("div.viewInfoTop h3");
                        Elements img = doc.select("span.img img");
                        Elements content = doc.select("div.txPoi iframe");
                        Elements headContent = doc.select("ul.tx li>span");
                        Elements date = doc.select("span.fr em");
                        Elements link = doc.select("p.receipt span>button");
                        String startDate = date.get(0).text().split("~")[0].split("\\(")[0];
                        String endDate = date.get(0).text().split("~")[1].split("\\(")[0];
                        String pagelink = null;
                        System.out.println("link : "+link);
                        System.out.println("size : "+link.size());
                        System.out.println("끝");
                        if(link.size() > 0){
                            pagelink = link.get(0).attr("onclick").split("'")[1];

                        }

                        contest.setTitle(title.get(0).text());
                        contest.setImg(img.attr("src"));
                        for (int j = 0; j < headContent.size(); j++) {
                            System.out.println(headContent.get(j).text());
                            switch (j) {
                                case 0: //주최자
                                    contest.setSupervise(headContent.get(j).text());
                                    break;
                                case 1: //주관
                                    contest.setSponsor(headContent.get(j).text());
                                    break;
                                case 2: //응모분야
                                    contest.setEnter(headContent.get(j).text());
                                    break;
                                case 3: //응모 대상
                                    contest.setTarget(headContent.get(j).text());
                                    break;
                                case 4: //주최사 유형
                                    contest.setHostType(headContent.get(j).text());
                                    break;
                                case 5: //시상 규모
                                    contest.setScale(headContent.get(j).text());
                                    break;
                                case 6: //특전
                                    contest.setBonus(headContent.get(j).text());
                                    break;
                                case 7: //1등 혜택
                                    contest.setBenefit(headContent.get(j).text());
                                    break;
                                default:    //여긴 오면 안됨 데이터 없음
                                    System.out.println("넌 뭐냐?");
                                    break;
                            }
                        }

                        String innerURL = TURL + content.attr("src");
                        innerDoc = Jsoup.connect(innerURL)
                                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
                                .get(); // 원하는 url에서 전체 구조를 받아온다
                        Elements body = innerDoc.select("body");
                        String strBody = body.get(0).toString();
                        strBody = strBody.replaceAll("\r\n","");
                        strBody = strBody.replace("<script type=\"text/javascript\">        $(window).load(function () {            var height = $(\"body\").height();            $(\"#Dtl_Gdln\", parent.document).height(height + 20);        });    </script>", "");
                        strBody = strBody.replace("<body>", "");
                        strBody = strBody.replace("</body>", "");
                        strBody = strBody.replaceAll("\r\n","");
                        contest.setBody(strBody);  //내용

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
                        Date startDate2 = simpleDateFormat.parse(startDate);
                        Date endDate2 = simpleDateFormat.parse(endDate);

                        contest.setStartDate(startDate2);
                        contest.setEndDate(endDate2);
                        contest.setLink(pagelink);

                        contest.setId(UUID.randomUUID().toString());

                        crawlingService.save(contest);
                    }
                }
//                Thread.sleep(7200000);//12시간 잠자기
//            }
        } catch (Exception e) {
            System.out.println("쓰레드 과로사");
            e.printStackTrace();
        }
    }

}