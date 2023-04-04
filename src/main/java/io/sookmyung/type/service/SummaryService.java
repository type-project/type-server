package io.sookmyung.type.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;

@Service
public class SummaryService {
    public Object makeSummary(String original_text) {

        // Spring restTemplate
        HashMap<String, Object> result = new HashMap<String, Object>();
        ResponseEntity<Object> resultMap = new ResponseEntity<>(null, null, 200);
        String url = "http://172.20.8.155:5000/summary";

        try {
            System.out.println("SummaryService.makeSummary");
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders header = new HttpHeaders();
            HttpEntity<?> entity = new HttpEntity<>(header);

            UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("original_text", original_text)
                    .build();


            resultMap = restTemplate.exchange(uriBuilder.toString(), HttpMethod.POST, entity, Object.class);

//            result.put("statusCode", resultMap.getStatusCode()); //http status code 를 확인
//            result.put("header", resultMap.getHeaders()); //헤더 정보 확인
            result.put("body", resultMap.getBody()); //실제 데이터 정보 확인
            System.out.println("SummaryService.makeSummary success!");

        } catch (HttpClientErrorException | HttpServerErrorException e) {
//            result.put("statusCode", e.getStatusCode());
//            result.put("body", e.getStatusText());
            result.put("body", null);
            System.out.println("http client ");
            System.out.println(e);

//            return resultMap;
        } catch (Exception e) {
//            result.put("statusCode", "999");
//            result.put("body", "exception thrown");
            result.put("body", null);
            System.out.println(e);

//            return resultMap;
        }

        System.out.println(result.get("body"));
        return result.get("body");
    }
}
