package com.bohutskyi.logtailer.event;

import org.springframework.context.ApplicationEvent;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Serhii Bohutksyi
 */
public class PrintBufferEvent {

    private List<String> list = new LinkedList<String>();

    public PrintBufferEvent(List<String> buffer) {
        list.addAll(buffer);
    }

    public List<String> getList() {
        return list;
    }
}
