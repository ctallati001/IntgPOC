package com.botw.poc.kyc.recert.jms.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
/**
 * Created by sharmd01 on 07/11/2017.
 */
@SpringBootApplication
@EnableJms
public class SpringBootJmsApp {


        public static void main(String[] args) {
            SpringApplication.run(com.botw.poc.kyc.recert.jms.core.SpringBootJmsApp.class, args);
        }

    }

