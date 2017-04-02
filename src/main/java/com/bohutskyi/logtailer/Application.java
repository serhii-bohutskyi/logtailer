package com.bohutskyi.logtailer;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author Serhii Bohutkyi
 */
@EnableAsync
@SpringBootApplication
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).headless(false).run(args);
    }
}
