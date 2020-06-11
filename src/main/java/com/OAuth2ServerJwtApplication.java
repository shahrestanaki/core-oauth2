package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication
public class OAuth2ServerJwtApplication {
  /*  @PostConstruct
   public void init(){
        //TimeZone.setDefault(TimeZone.getTimeZone("Asia/Tehran"));   // It will set UTC timezone
       //// LocalDateTime local = LocalDateTime.now();
       // local.atZone(ZoneId.systemDefault());
       // TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.systemDefault()));
        System.out.println("Spring boot application running in UTC timezone :"+new Date());   // It will print UTC timezone

    }*/

    public static void main(String... args) {
        SpringApplication.run(OAuth2ServerJwtApplication.class, args);
    }

}
