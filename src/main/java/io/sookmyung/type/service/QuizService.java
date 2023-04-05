package io.sookmyung.type.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import io.sookmyung.type.controller.dto.QuizDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Duration;
import java.util.List;

@RequiredArgsConstructor
@Service
public class QuizService {
    private static final String baseUrl = "http://172.20.8.155:5000";

    public List<QuizDto> generateQuiz(String original_text) {

        System.out.println("QuizService.generateQuiz");
//        ResponseEntity<QuizDto> resultMap;

        System.out.println(original_text);

//        InetAddress inetAddress = null;
//        try {
//            inetAddress = InetAddress.getByName(baseUrl);
//            if (!inetAddress.isReachable(3000)) {
//                System.err.println("server is not reachable");
//                return null;
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        UriComponents uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path("/keyword2")
                .queryParam("original_text", original_text)
                .build();
        System.out.println("uri = " + uri);

        // Spring restTemplate (sync)
        try {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
            restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(3));
            restTemplateBuilder.setReadTimeout(Duration.ofSeconds(3));
            RestTemplate restTemplate = restTemplateBuilder.build();

            HttpHeaders header = new HttpHeaders();
            HttpEntity<Object> entity = new HttpEntity<>(header);

            ResponseEntity<Object> response = restTemplate.postForEntity(uri.toString(), entity, Object.class);

            System.out.println("response.getStatusCode() = " + response.getStatusCode());
            System.out.println("response.getHeaders() = " + response.getHeaders());
            System.out.println("response.getBody() = " + response.getBody());
            ObjectMapper mapper = new ObjectMapper();
            List<QuizDto> quizDtoList = mapper.convertValue(response.getBody(),
                    TypeFactory.defaultInstance().constructCollectionType(List.class, QuizDto.class));
//            QuizDto quizGenerateDto = mapper.convertValue(response.getBody(), QuizDto.class);
            for (QuizDto quiz : quizDtoList) {
                System.out.println("quiz = " + quiz);
            }

            return quizDtoList;
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            System.err.println("http client ");
            System.err.println(e);
        } catch (Exception e) {
            System.err.println(e);
        }
        return null;
    }
}
