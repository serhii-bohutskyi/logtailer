package com.bohutskyi.logtailer;

import com.bohutskyi.logtailer.ui.MainFrameUi;
import com.jcraft.jsch.JSch;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author Serhii Bohutkyi
 */
@SpringBootApplication
@EnableAutoConfiguration
public class Application {

    @Bean
    public BlockingQueue<List<String>> logUiQueue() {
        return new LinkedBlockingDeque<List<String>>(1000);
    }

    @Bean
    public BlockingQueue<List<String>> logFileQueue() {
        return new LinkedBlockingDeque<List<String>>(1000);
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
