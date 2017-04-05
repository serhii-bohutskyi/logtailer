package com.bohutskyi.logtailer.service;

import com.bohutskyi.logtailer.model.SshConfigModel;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Serhii Bohutksyi
 */
@Service
public class Converter {

    public SshConfigModel convert(Map<FormParameter, String> map) {
        SshConfigModel sshConfigModel = new SshConfigModel();
        sshConfigModel.setHost(map.get(FormParameter.HOST));
        sshConfigModel.setPort(Integer.valueOf(map.get(FormParameter.PORT)));
        sshConfigModel.setUsername(map.get(FormParameter.USERNAME));
        sshConfigModel.setPassword(map.get(FormParameter.PASSWORD));
        sshConfigModel.setServerLogPath(map.get(FormParameter.SERVER_LOG_PATH));
        sshConfigModel.setLocalLogPath(map.get(FormParameter.LOCAL_LOG_PATH));
        return sshConfigModel;
    }
}
