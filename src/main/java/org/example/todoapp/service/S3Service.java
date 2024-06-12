package org.example.todoapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor //생성자 주입을 위해
public class S3Service {
    private final S3Client s3Client;
    private final String bucketName= "ddaru-s3-bucket"; //s3에서 만든 내 버킷 이름 쓰기

//    public void uploadFile(MultipartFile multipartFile) throws IOException {
//        String key=multipartFile.getOriginalFilename();
//
//        s3Client.putObject(
//                PutObjectRequest.builder()
//                        .bucket(bucketName)
//                        .key(key)
//                        .build(),
//                RequestBody.fromBytes(multipartFile.getBytes())
//        );
//    }

    public void uploadFile(MultipartFile multipartFile, String key) throws IOException {
//        String key=multipartFile.getOriginalFilename();

        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build(),
                RequestBody.fromBytes(multipartFile.getBytes())
        );
    }

    public InputStream downloadFile(String key){
        return s3Client.getObject(
                GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build()
        );
    }


}
