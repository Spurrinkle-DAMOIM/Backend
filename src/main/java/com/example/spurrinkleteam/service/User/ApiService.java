package com.example.spurrinkleteam.service.User;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
public class ApiService {

    public String getToken()throws IOException{
        HttpsURLConnection conn = null;
        URL url = new URL("https://api.iamport.kr/users/getToken");
        // URL 연결 (웹페이지 URL 연결.)
        conn = (HttpsURLConnection)  url.openConnection();

        // 요청 방식 선택 (GET, POST)
        conn.setRequestMethod("POST");
        // 타입설정(text/html) 형식으로 전송 (Request Body 전달시 application/xml로 서버에 전달.)
        conn.setRequestProperty("Content-type", "application/json");
        // 서버 Response Data를 json 형식의 타입으로 요청.
        conn.setRequestProperty("Accept", "application/json");
        // OutputStream으로 POST 데이터를 넘겨주겠다는 옵션.
        conn.setDoOutput(true);

        JsonObject json = new JsonObject();
        json.addProperty("imp_key", "");
        json.addProperty("imp_secret",
                "");
//        json.addProperty("imp_key", "자기키");
//        json.addProperty("imp_secret",
//                "자기 비밀키");

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

        // 출력
        bw.write(json.toString());
        // 버퍼 비움
        bw.flush();
        bw.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

        Gson gson = new Gson();
        // Json 문자열 -> Map 객체로 변환 후 response 키 값의 value값을 추출
        String response = gson.fromJson(br.readLine(), Map.class).get("response").toString();

        System.out.println(response);
        // Json 문자열 -> Map 객체로 변환 후 access_token 키 값의 value값을 추출
        String token = gson.fromJson(response, Map.class).get("access_token").toString();

        br.close();
        conn.disconnect();

        return token;
    }

    public Map<String, String> CertificationCheck(String token, String imp_uid) throws IOException, ParseException {
        System.out.println("token : " + token + " imp_uid : " + imp_uid);
        Map<String, String> map = new HashMap<>();
        HttpsURLConnection conn = null;
        URL url = new URL("https://api.iamport.kr/certifications/" + imp_uid);
        // URL 연결 (웹페이지 URL 연결.)
        conn = (HttpsURLConnection) url.openConnection();

        // 요청 방식 선택 (GET, POST)
        conn.setRequestMethod("GET");
        // 타입설정(text/html) 형식으로 전송 (Request Body 전달시 application/xml로 서버에 전달.)
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Authorization", token);
        // 서버 Response Data를 json 형식의 타입으로 요청.
//        conn.setRequestProperty("Accept", "application/json");
        // OutputStream으로 POST 데이터를 넘겨주겠다는 옵션.
        conn.setDoOutput(true);

        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            System.out.println(line);
            sb.append(line);
        }

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) jsonParser.parse(sb.toString());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        JSONObject PetInfoResult = (JSONObject) jsonObject.get("response");
        conn.disconnect();
        map.put("name", String.valueOf(PetInfoResult.get("name")));
        map.put("phone", String.valueOf(PetInfoResult.get("phone")));
        map.put("birthday", String.valueOf(PetInfoResult.get("birthday")));
        return map;

    }
}
