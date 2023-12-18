package com.grupo5.AlquilerEquiposConstruccion.utils;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    public AmazonS3 amazonS3() {

        AWSCredentials credentials = new BasicAWSCredentials(
                "AKIAY3PLHSUJCOQE5HPE",
                "edla3tGu+0R/Yz4tUlT8eQETIKFFuN1u4Ph0md6t"
        );

        return (AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_WEST_1)
                .build());
    }

}
