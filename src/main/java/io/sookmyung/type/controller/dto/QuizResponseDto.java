package io.sookmyung.type.controller.dto;

import io.sookmyung.type.repository.Quiz;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class QuizResponseDto {
    private final String forward;
    private final String keyword;
    private final String backward;

    public QuizResponseDto(String keyword, String quiz) {
        List<String> string01 = truncateString(quiz);
        this.forward = string01.get(0);
        this.keyword = keyword;
        this.backward = string01.get(1);
    }

    // -------------------- * method * -------------------- //
    private List<String> truncateString(String inputString) {
        String delimiter = "@@@";

        int delimiterIndex = inputString.indexOf(delimiter);
        System.out.println("delimiterIndex = " + delimiterIndex);
        List<String> string01 = new ArrayList<>();
        if (delimiterIndex != -1) {
            String forwardString = inputString.substring(0, delimiterIndex);
            String backwardString = inputString.substring(delimiterIndex + delimiter.length());
            string01.add(forwardString);
            string01.add(backwardString);
        } else {
            string01.add(null);
            string01.add(null);
        }


        return string01;
    }
}
