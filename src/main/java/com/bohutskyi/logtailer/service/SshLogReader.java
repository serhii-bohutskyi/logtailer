package com.bohutskyi.logtailer.service;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Serhii Bohutskyi
 */
@Component
public class SshLogReader extends Thread {

    private static final Integer BUFFER_SIZE = 10;
    @Autowired
    @Qualifier("logUiQueue")
    private BlockingQueue<List<String>> logUiQueue;
    @Autowired
    @Qualifier("logFileQueue")
    private BlockingQueue<List<String>> logFileQueue;
    @Autowired
    private SshClient sshClient;
    private ChannelExec channel;
    private AtomicBoolean isSaving = new AtomicBoolean(false);
    private String serverLogPath;

    public void startRead(String serverLogPath) {

        if (StringUtils.isEmpty(serverLogPath)) {
            throw new IllegalStateException("Server log path must be not empty!");
        }
        if (!sshClient.getSession().isPresent()) {
            throw new IllegalStateException("Session not opened! Connect first!");
        }
        setServerLogPath(serverLogPath);

        start();
    }

    public void stopRead() {
        interrupt();
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            ChannelExec channelExec = null;
            try {
                channelExec = openChannel();
                InputStream inputStream = channelExec.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                List<String> buffer = new ArrayList<>();
                for (int j = 0; j < 3; j++) {//trying 3 times to connect

                    if (bufferedReader.ready()) {
                        while (!isInterrupted()) {
                            String line = bufferedReader.readLine();
                            if (line != null) {//is it EOF, wait for a new lines
                                buffer.add(line);
                                if (buffer.size() == BUFFER_SIZE) {

                                    List<String> temp = copy(buffer);
                                    logUiQueue.put(temp);
                                    if (getIsSaving().get()) {
                                        logFileQueue.put(temp);
                                    }
                                    buffer.clear();
                                }
                            } else {
                                Thread.sleep(100);
                            }
                        }
                    }

                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                e.printStackTrace(); //todo
            }
            if (channelExec != null && !channelExec.isClosed()) {
                channelExec.disconnect();
            }
        }
    }

    private List<String> copy(List<String> from) {
        List<String> to = new ArrayList<>();
        to.addAll(from);
        return to;
    }

    private ChannelExec openChannel() throws JSchException, IOException {
        ChannelExec channelExec = (ChannelExec) sshClient.getSession().get().openChannel("exec");
        channelExec.setPty(true);
        String cmd = "tail -f " + getServerLogPath();
        channelExec.setCommand(cmd);
        channelExec.connect();
        return channelExec;
    }


    private AtomicBoolean getIsSaving() {
        return isSaving;
    }

    public void setIsSaving(Boolean saving) {
        this.isSaving.set(saving);
    }

    private String getServerLogPath() {
        return serverLogPath;
    }

    private void setServerLogPath(String serverLogPath) {
        this.serverLogPath = serverLogPath;
    }
}
