package io.sookmyung.type.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
public class S3FileUploader {
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    private final AmazonS3 s3Client;

    @Autowired
    public S3FileUploader(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFileToS3(MultipartFile multipartFile) throws IOException {
        // Convert MultipartFile to File
        File file = convertMultipartFileToFile(multipartFile);
        String fileName = file.getName();

        // Upload image to bucket
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(multipartFile.getSize());
            objectMetadata.setContentType(multipartFile.getContentType());

            System.out.println("bucketName = " + bucketName);
            System.out.println("file.getName() = " + fileName);
            System.out.println("file.getAbsolutePath() = " + file.getAbsolutePath());
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, file);
            putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
            s3Client.putObject(putObjectRequest);
        } catch (SdkClientException e) {
            throw new RuntimeException("Error uploading file to S3: " + e.getMessage());
        } finally {
            // Delete the temporary file
            if (file.exists()) {
                file.delete();
            }
        }

        return s3Client.getUrl(bucketName, fileName).toString();
    }

    private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        // Create a new temporary file with a unique name
        File file = File.createTempFile("thumbnail-", ".jpeg");

        // Read the multipart file content into a BufferedImage object
        BufferedImage image = ImageIO.read(multipartFile.getInputStream());

        // Create a new scaled instance of the image using the thumbnail size
        int thumbnailWidth = 200; // Change this to your desired thumbnail width
        int thumbnailHeight = 100; // Change this to your desired thumbnail height
        BufferedImage thumbnail = new BufferedImage(thumbnailWidth, thumbnailHeight, BufferedImage.TYPE_INT_RGB);
        thumbnail.createGraphics().drawImage(
                image.getScaledInstance(thumbnailWidth, thumbnailHeight, java.awt.Image.SCALE_SMOOTH),
                0,
                0,
                null);

        // Write the thumbnail image to the temporary file as a JPEG
        ImageIO.write(thumbnail, "jpeg", file);

        return file;
    }
}