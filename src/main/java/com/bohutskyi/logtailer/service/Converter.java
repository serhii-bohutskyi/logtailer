package com.bohutskyi.logtailer.service;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Serhii Bohutksyi
 */
@Service
public class Converter {

    public TailModel convert(Map<FormParameter, String> map) {
        TailModel tailModel = new TailModel();
        tailModel.setHost(map.get(FormParameter.HOST));
        tailModel.setPort(Integer.valueOf(map.get(FormParameter.PORT)));
        tailModel.setUsername(map.get(FormParameter.USERNAME));
        tailModel.setPassword(map.get(FormParameter.PASSWORD));
        tailModel.setServerLogPath(map.get(FormParameter.SERVER_LOG_PATH));
        return tailModel;
    }
}
