package com.grupo5.AlquilerEquiposConstruccion.service.impl;

import com.grupo5.AlquilerEquiposConstruccion.service.S3Service;
import com.grupo5.AlquilerEquiposConstruccion.utils.S3Config;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;


@Service
@NoArgsConstructor
public class S3ServiceImpl implements S3Service {

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @Autowired
    private S3Config s3Config;

    public void uploadFile(String key, File file) {
        s3Config.amazonS3().putObject(bucketName, key, file);
    }

    public void deleteFile(String key) {
        s3Config.amazonS3().deleteObject(bucketName, key);
    }

//    public S3Object downloadFile(String key) {
//        return amazonS3.getObject(bucketName, key);
//    }
}