package com.bohutskyi.logtailer.event;

import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * @author Serhii Bohutksyi
 */
public class ShowErrorsEvent {
    private List<String> errors;

    public ShowErrorsEvent(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
