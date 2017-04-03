package com.bohutskyi.logtailer;

import com.bohutskyi.logtailer.ui.MainFrameUi;
import com.jcraft.jsch.JSch;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author Serhii Bohutkyi
 */
@EnableAsync
@SpringBootApplication
@EnableAutoConfiguration
public class Application {

    @Bean
    public BlockingQueue<String> logQueue() {
        return new ArrayBlockingQueue<String>(1000);
    }

    @Bean
    public MainFrameUi mainFrameUi() {
        return new MainFrameUi();
    }

    @Bean
    public JSch jsch() {
        return new JSch();
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).headless(false).run(args);
    }
}
