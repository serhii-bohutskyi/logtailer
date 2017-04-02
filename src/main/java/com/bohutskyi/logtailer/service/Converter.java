package com.bohutskyi.logtailer.service;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Serhii Bohutksyi
 */
@Service
public class Converter {

    public TailData convert(Map<FormParameter, String> map) {
        TailData tailData = new TailData();
        tailData.setHost(map.get(FormParameter.HOST));
        tailData.setPort(Integer.valueOf(map.get(FormParameter.PORT)));
        tailData.setUsername(map.get(FormParameter.USERNAME));
        tailData.setPassword(map.get(FormParameter.PASSWORD));
        tailData.setServerLogPath(map.get(FormParameter.SERVER_LOG_PATH));
        return tailData;
    }
}
