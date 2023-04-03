package io.sookmyung.type.service;

import com.google.cloud.speech.v1.*;
import com.google.protobuf.ByteString;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding;
import static com.google.cloud.speech.v1.RecognitionConfig.newBuilder;

@RequiredArgsConstructor
@Service
public class SpeechToTextService {
    private final Logger logger = LoggerFactory.getLogger(SpeechToTextService.class);

    public String transcribe(MultipartFile audioFile) throws IOException {
        if (audioFile.isEmpty()) {
            throw new IOException("Required part 'audioFile' is not present.");
        }

        // 오디오 파일을 바이트 배열로 디코딩
        byte[] audioBytes = audioFile.getBytes();

        // 클라이언트 인스턴스화
        try (SpeechClient speechClient = SpeechClient.create()) {
            // 오디오 객체 생성
            ByteString audioData = ByteString.copyFrom(audioBytes);
            RecognitionAudio recognitionAudio = RecognitionAudio.newBuilder()
                    .setContent(audioData)
                    .build();

            // 요청 처리 정보 생성
//            AudioEncoding audioEncoding = AudioEncoding.LINEAR16;
//            int sampleRateHertz = 16000;
//            String languageCode = "ko-KR";
            AudioEncoding audioEncoding = AudioEncoding.FLAC;
            int sampleRateHertz = 44100;
            String languageCode = "en-US";

            RecognitionConfig recognitionConfig = newBuilder()
                    .setEncoding(audioEncoding)
                    .setSampleRateHertz(sampleRateHertz)
                    .setLanguageCode(languageCode)
                    .build();

            // 트랜스크립션 수행
            RecognizeResponse response = speechClient.recognize(recognitionConfig, recognitionAudio);
            List<SpeechRecognitionResult> results = response.getResultsList();

            if (!results.isEmpty()) {
                // 주어진 말 뭉치에 대해 여러 가능한 스크립트를 제공. 0번(가장 가능성 있는)을 사용한다.
                SpeechRecognitionResult result = results.get(0);
                return result.getAlternatives(0).getTranscript();
            } else {
                logger.error("No transcription result found");
                return "";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

