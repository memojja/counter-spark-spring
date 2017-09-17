package com.example.spark.config;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SparkConfig {

    @Value("${app.name:counter}")
    private String appName;

//    @Value("${spark.home}")
//    private String sparkHome;

    @Value("${master.uri:local}")
    private String masterUri;

    @Bean
    public SparkConf sparkConf(){
        return new SparkConf()
                .setAppName(appName)
//                .setSparkHome(sparkHome)
                .setMaster(masterUri);
    }

    @Bean
    public JavaSparkContext javaSparkContext(){
        JavaSparkContext javaSparkContext =new JavaSparkContext(sparkConf());
        javaSparkContext.setLogLevel("OFF");
        return javaSparkContext;
    }

    @Bean
    public SparkSession sparkSession(){
        return SparkSession
                .builder()
                .sparkContext(javaSparkContext().sc())
                .appName("Counter Example")
                .getOrCreate();
    }

}
