package com.example.spurrinkleteam;


import com.example.spurrinkleteam.crawling.Crawling;
import com.example.spurrinkleteam.entity.Todo;
import com.example.spurrinkleteam.repository.TodoRepository;
import com.example.spurrinkleteam.service.TodoService;
import org.json.simple.JSONArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class SpurrinkleteamApplicationTests {

}
