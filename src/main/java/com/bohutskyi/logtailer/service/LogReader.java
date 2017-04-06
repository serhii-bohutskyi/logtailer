package com.bohutskyi.logtailer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Serhii Bohutskyi
 */
@Component
public class LogReader {

    private static final Integer COUNT_EMPTY_TO_FLUSH = 5;
    private static final Integer BUFFER_SIZE = 10;
    @Autowired
    private BlockingQueue<List<String>> logUiQueue;
    @Autowired
    private BlockingQueue<List<String>> logFileQueue;

    private final AtomicBoolean isSendToUi = new AtomicBoolean();
    private final AtomicBoolean isSendToFile = new AtomicBoolean();

    private InputStream inputStream;
    private LogReaderThread logReaderThread;

    public void start(InputStream inputStream) {
        setInputStream(inputStream);
        if (getLogReaderThread().isPresent() && getLogReaderThread().get().isAlive()) {
            getLogReaderThread().get().interrupt();
        }
        setLogReaderThread(new LogReaderThread());
        getLogReaderThread().get().start();
    }

    public void sendToUi() {
        isSendToUi.set(true);
    }

    public void sendToFile() {
        isSendToFile.set(true);
    }

    public boolean isAlive() {
        return getLogReaderThread().isPresent() && getLogReaderThread().get().isAlive();
    }


    private class LogReaderThread extends Thread {

        LogReaderThread() {
            super("LogReaderThread");
        }

        @Override
        public void run() {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getInputStream()))) {

                List<String> buffer = new ArrayList<>();
                for (int j = 0; j < 3; j++) {//trying 3 times to connect

                    if (bufferedReader.ready()) {
                        while (!isInterrupted()) {
                            String line = bufferedReader.readLine();
                            int countTries = 0;
                            if (line != null) {//check is it EOF, wait for a new lines
                                buffer.add(line);
                                if (buffer.size() == BUFFER_SIZE) {
                                    flush(buffer);
                                }
                            } else {
                                Thread.sleep(100);
                                countTries++;
                            }

                            if (countTries == COUNT_EMPTY_TO_FLUSH) {
                                flush(buffer);
                            }
                        }
                    }

                    if (isInterrupted()) {
                        break;
                    }

                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                e.printStackTrace(); //todo
            }
        }
    }


    private void flush(List<String> buffer) throws InterruptedException {
        List<String> temp = copy(buffer);
        if (getIsSendToUi().get()) {
            logUiQueue.put(temp);
        }
        if (getIsSendToFile().get()) {
            logFileQueue.put(temp);
        }
        for (String s : temp) {
            System.out.println(s);
        }
        buffer.clear();
    }

    private AtomicBoolean getIsSendToUi() {
        return isSendToUi;
    }

    private AtomicBoolean getIsSendToFile() {
        return isSendToFile;
    }

    private InputStream getInputStream() {
        return inputStream;
    }

    private void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    private List<String> copy(List<String> from) {
        List<String> to = new ArrayList<>();
        to.addAll(from);
        return to;
    }

    public void stopSendToFile() {
        isSendToFile.set(false);
    }

    public void stopSendToUi() {
        isSendToUi.set(false);
    }

    private Optional<LogReaderThread> getLogReaderThread() {
        return Optional.ofNullable(logReaderThread);
    }

    private void setLogReaderThread(LogReaderThread logReaderThread) {
        this.logReaderThread = logReaderThread;
    }

    public void stopRead() {
        if (getLogReaderThread().isPresent() && getLogReaderThread().get().isAlive()) {
            getLogReaderThread().get().interrupt();
        }
        setLogReaderThread(null);
        isSendToUi.set(false);
        isSendToFile.set(false);
        logUiQueue.clear();
        logFileQueue.clear();
    }
}
