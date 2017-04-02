package com.bohutskyi.logtailer.event;

import com.bohutskyi.logtailer.service.FormParameter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Serhii Bohutksyi
 */
public class StartTailEvent {

    private Map<FormParameter, String> parameters;

    public StartTailEvent(Map<FormParameter, String> parameters) {
        this.parameters = parameters;
    }

    public Map<FormParameter, String> getParameters() {
        return parameters;
    }
}
