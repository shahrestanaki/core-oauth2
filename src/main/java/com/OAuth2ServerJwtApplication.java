package com;

import com.tools.GetResourceBundle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication

public class OAuth2ServerJwtApplication {
    public static void main(String... args) {
        SpringApplication.run(OAuth2ServerJwtApplication.class, args);
        System.out.println("-------------------- Application start in " + new Date() +
                " - on port: " + GetResourceBundle.getApplication.getString("server.port") + " ----------------------");
    }

}
