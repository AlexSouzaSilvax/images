package com.amazons3.images.service;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class S3Service {

        private final S3Client s3Client;

        @Value("${aws.s3.bucket}")
        private String bucketName;

        public S3Service(@Value("${aws.accessKey}") String accessKey,
                        @Value("${aws.secretKey}") String secretKey,
                        @Value("${aws.region}") String region) {
                this.s3Client = S3Client.builder()
                                .region(Region.of(region))
                                .credentialsProvider(StaticCredentialsProvider.create(
                                                AwsBasicCredentials.create(accessKey, secretKey)))
                                .build();
        }

        public String uploadBase64Image(String nome, String base64Image) throws IOException {
                byte[] imageBytes = Base64.getDecoder().decode(base64Image);
                String fileName = nome + ".png";

                PutObjectRequest putRequest = PutObjectRequest.builder()
                                .bucket(bucketName)
                                .key(fileName)
                                .contentType("image/png")
                                .build();

                s3Client.putObject(putRequest, software.amazon.awssdk.core.sync.RequestBody
                                .fromByteBuffer(ByteBuffer.wrap(imageBytes)));

                return "https://" + bucketName + ".s3.amazonaws.com/" + fileName;
        }
}
