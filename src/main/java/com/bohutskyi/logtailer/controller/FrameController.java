package com.bohutskyi.logtailer.controller;

import com.bohutskyi.logtailer.model.SshConfigModel;
import com.bohutskyi.logtailer.service.BufferedFileWriter;
import com.bohutskyi.logtailer.service.Converter;
import com.bohutskyi.logtailer.service.FormParameter;
import com.bohutskyi.logtailer.service.LogReaderThread;
import com.bohutskyi.logtailer.service.SshClientImpl;
import com.bohutskyi.logtailer.ui.MainFrame;
import com.bohutskyi.logtailer.ui.Notifications;
import com.bohutskyi.logtailer.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * @author Serhii Bohutksyi
 */
@Component
public class FrameController extends AbstractFrameController {

    @Autowired
    private MainFrame mainFrame;
    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private Converter converter;
    @Autowired
    private Validator validator;
    @Autowired
    private SshClientImpl sshClient;
    @Autowired
    private BufferedFileWriter bufferedFileWriter;
    @Autowired
    private LogReaderThread logReaderThread;

    @PostConstruct
    public void init() {
        registerListener(mainFrame.getTailButton(), (e) -> onTailButtonClicked());
        registerListener(mainFrame.getTailToFileButton(), (e) -> {
            onTailToFileButtonClicked();
        });
    }

    private void onTailToFileButtonClicked() {
        if (mainFrame.isTailingToFile()) {
            stopTailingToFile();
        } else {
            startTailingToFile();
        }
    }

    private void startTailingToFile() {
        mainFrame.startTailToFileUi();

        Map<FormParameter, String> parameters = mainFrame.getParameters();

        List<String> errors = validator.validate(parameters, true);
        if (errors.size() > 0) {
            mainFrame.stopTailToFileUi();
            Notifications.showFormValidationAlert(errors);
            return;
        }
        SshConfigModel sshConfigModel = converter.convert(parameters);

        sshClient.connect(sshConfigModel);
        logReaderThread.startRead(sshConfigModel.getServerLogPath());
        logReaderThread.setIsSaving(true);

        bufferedFileWriter.startWrite(sshConfigModel.getLocalLogPath());
    }

    private void stopTailingToFile() {

    }

    public void onTailButtonClicked() {
        if (mainFrame.isTailing()) {
            stopTailing();
        } else {
            startTailing();
        }
    }

    private void startTailing() {
        mainFrame.startTailUi();

        Map<FormParameter, String> parameters = mainFrame.getParameters();

        List<String> errors = validator.validate(parameters, false);
        if (errors.size() > 0) {
            mainFrame.stopTailUi();
            Notifications.showFormValidationAlert(errors);
            return;
        }

        SshConfigModel sshConfigModel = converter.convert(parameters);
        sshClient.connect(sshConfigModel);
        logReaderThread.startRead(sshConfigModel.getServerLogPath());
        logReaderThread.setIsSaving(false);
    }

    private void stopTailing() {
        mainFrame.stopTailUi();
        sshClient.disconnect();
    }


}
