package io.sookmyung.type.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Service
public class AudioConverter {
    private static final String FFMPEG_COMMAND = "ffmpeg";

    public File convertM4AToFlac(MultipartFile multipartFile) throws Exception {

        File inputFile = new File(multipartFile.getOriginalFilename());

        try {
            inputFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(inputFile);
            fos.write(multipartFile.getBytes());
            fos.close();
            System.out.println(inputFile.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String outFileName = "m4a" + nowDateTime() + ".flac";
        File outputFile = new File(outFileName);

        // Delete the same exist file
        if (deleteFile(outputFile)) System.out.println("output file reset successfully");

        // Run FFmpeg to transcode the input m4a file to flac
        ProcessBuilder processBuilder = new ProcessBuilder(
                FFMPEG_COMMAND,
                "-i", inputFile.getAbsolutePath(),
                "-ac", "1",
                "-ar", "16000",
                "-sample_fmt", "s16",
                "-f", "flac",
                outputFile.getAbsolutePath());
        Process process = processBuilder.start();
        process.waitFor();

        deleteFile(inputFile);
        printFFmpegStream(process);

        // Check if output file exists
        if (outputFile.exists()) {
            System.out.println("Output file created at " + outputFile.getAbsolutePath());
        } else {
            System.err.println("Output file not created.");
            return null;
        }

        return outputFile;
    }

    public boolean deleteFile(File outputFile) {
        // If already exist output file, occurs long duration
        if (outputFile.exists()) return outputFile.delete();
        else return false;
    }

    private static void printFFmpegStream(Process process) throws IOException {
        // Print output and error streams from FFmpeg
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.err.println(line);
            }
        }
    }

    private static String nowDateTime() {

        // 현재 날짜 구하기
        LocalDateTime now = LocalDateTime.now();

        // 포맷 정의
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddHHmmss");

        // 포맷 적용
        String formattedNow = now.format(formatter);

        // 결과 출력
        System.out.println(formattedNow);  // 0320205500
        return formattedNow;
    }
}
