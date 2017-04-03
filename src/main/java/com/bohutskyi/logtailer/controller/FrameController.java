package com.bohutskyi.logtailer.controller;

import com.bohutskyi.logtailer.service.Converter;
import com.bohutskyi.logtailer.service.FormParameter;
import com.bohutskyi.logtailer.service.SshClient;
import com.bohutskyi.logtailer.service.TailModel;
import com.bohutskyi.logtailer.ui.MainFrame;
import com.bohutskyi.logtailer.ui.Notifications;
import com.bohutskyi.logtailer.validator.Validator;
import com.jcraft.jsch.JSchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
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
    private SshClient sshClient;

    @PostConstruct
    public void init() {
        registerListener(mainFrame.getTailButton(), (e) -> onTailButtonClicked());
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

        List<String> errors = validator.validate(parameters);
        if (errors.size() > 0) {
            mainFrame.stopTailUi();
            Notifications.showFormValidationAlert(errors);
            return;
        }

        TailModel tailModel = converter.convert(parameters);

        try {
            sshClient.connect(tailModel);
        } catch (JSchException e) {
            mainFrame.stopTailUi();
            Notifications.showFormValidationAlert(Collections.singletonList(e.getMessage()));
            return;
        }
    }

    private void stopTailing() {
        mainFrame.stopTailUi();
        sshClient.disconnect();
    }


}
