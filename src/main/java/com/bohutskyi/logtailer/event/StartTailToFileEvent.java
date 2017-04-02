package com.bohutskyi.logtailer.event;

/**
 * @author Serhii Bohutksyi
 */
public class StartTailToFileEvent {
    private String localLogPath;

    public StartTailToFileEvent(String localLogPath) {
        this.localLogPath = localLogPath;
    }

    public String getLocalLogPath() {
        return localLogPath;
    }
}
