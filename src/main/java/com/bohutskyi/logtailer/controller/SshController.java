package com.bohutskyi.logtailer.controller;

import com.bohutskyi.logtailer.event.*;
import com.bohutskyi.logtailer.service.Converter;
import com.bohutskyi.logtailer.service.SshClient;
import com.bohutskyi.logtailer.service.TailData;
import com.bohutskyi.logtailer.validator.Validator;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * @author Serhii Bohutksyi
 */
@Component
public class SshController {

    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private Converter converter;
    @Autowired
    private Validator validator;
    @Autowired
    private SshClient sshClient;

    @EventListener
    public void handleStartTailEvent(StartTailEvent startTailEvent) throws JSchException, IOException, InterruptedException {

        List<String> errors = validator.validate(startTailEvent.getParameters());
        if (errors.size() > 0) {
            publisher.publishEvent(new ShowErrorsEvent(errors));
            return;
        }

        TailData tailData = converter.convert(startTailEvent.getParameters());

        sshClient.connect(tailData);
        sshClient.tail(tailData.getServerLogPath());

    }

    @EventListener
    public void handleStartTailToFileEvent(StartTailToFileEvent startTailToFileEvent) {

    }

    @EventListener
    public void handleStopTailEvent(StopTailEvent stopTailEvent) {

    }

    @EventListener
    public void handleStopTailToFileEvent(StopTailToFileEvent stopTailToFileEvent) {

    }

}
