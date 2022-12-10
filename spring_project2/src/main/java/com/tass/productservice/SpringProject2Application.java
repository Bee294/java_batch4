package com.tass.productservice;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication

public class SpringProject2Application {

    public static void main(String[] args) {

        SpringApplication.run(SpringProject2Application.class, args);
    }
//    @EventListener(ApplicationReadyEvent.class)
//    private void serverReady(){
//        System.out.println("==================================================");
//        System.out.println("|         WORKFLOW  SERVICE SERVER IS READY       |");
//        System.out.println("|         DEPLOY BY DUC (duc29.work@gmail.com)    |");
//        System.out.println("==================================================");
//        System.out.println("--------------------------------------------------");
//
//        System.out.println("--------------------------------------------------");
//
//        log.info("==================================================");
//        log.info("|        PRODUCT  SERVICE SERVER IS READY       |");
//        log.info("|        DEPLOY BY DUC (duc29.work@gmail.com)   |");
//        log.info("|                   STARTED                     |");
//        log.info("==================================================");
//        log.info("-----------------------------------");
//    }

}
