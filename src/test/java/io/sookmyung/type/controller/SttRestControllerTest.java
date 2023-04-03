package io.sookmyung.type.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.FileInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SttRestControllerTest {
    private final Logger logger = LoggerFactory.getLogger(SttRestControllerTest.class);

    @Autowired
    /**
     * 웹 API 테스트할 때 사용
     * 스프링 MVC 테스트의 시작점
     * HTTP GET,POST 등에 대해 API 테스트 가능
     * */
    private MockMvc mockMvc;

    @Test
    @DisplayName("STT CONTROLLER : /stt/audio")
    public void testHandleAudioMessage() throws Exception {
        //given
        // Create a mock audio file
        final String fileName = "test-audio";
        final String fileType = "flac";
        final String filePath = "src/test/resources/" + fileName + "." + fileType;
        FileInputStream audioFileInputStream = new FileInputStream(filePath);

        MockMultipartFile audioFile = new MockMultipartFile(
                "audioFile",
                fileName + "." + fileType,
                fileType,
                audioFileInputStream);

        // Set userSeq and slideIdx values
        Long userSeq = 1234L;
        Integer slideIdx = 5;

        //when
        //then
        // Call the controller endpoint
        MvcResult result = mockMvc.perform(multipart("/stt/audio")
                        .file(audioFile)
                        .param("userSeq", String.valueOf(userSeq))
                        .param("slideIdx", String.valueOf(slideIdx)))
                .andExpect(status().isOk())
                .andReturn();

        // Verify the response
        String responseBody = result.getResponse().getContentAsString();
        String expected = "several tornadoes touched down as a line of severe thunderstorms swept through Colorado on Sunday";
        assertEquals(expected, responseBody);
    }
}