package com.bohutskyi.logtailer.service;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Serhii Bohutksyi
 */
@Component
public class SshClient extends Thread {

    private static final Integer BUFFER_SIZE = 10;
    @Autowired
    private BlockingQueue<List<String>> logUiQueue;
    @Autowired
    private BlockingQueue<List<String>> logFileQueue;
    @Autowired
    private JSch jsch;
    private AtomicBoolean isTailToFile = new AtomicBoolean();
    private TailModel tailModel;
    private Optional<Session> session = Optional.empty();

    public void setIsTailToFile(AtomicBoolean isTailToFile) {
        this.isTailToFile = isTailToFile;
    }

    public TailModel getTailModel() {
        return tailModel;
    }

    public void setTailModel(TailModel tailModel) {
        this.tailModel = tailModel;
    }

    public void connect() throws JSchException {
        Session jschSession = jsch.getSession(tailModel.getUsername(), tailModel.getHost(), tailModel.getPort());
        jschSession.setPassword(tailModel.getPassword());
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        jschSession.setConfig(config);
        jschSession.connect(15000);
        jschSession.setServerAliveInterval(15000);

        session = Optional.of(jschSession);
    }

    public void tail() throws JSchException {
        if (!session.isPresent() || !session.get().isConnected()) {
            connect();//reconnect
        }
        notify();
    }

    @PreDestroy
    public void disconnect() {
        if (!isInterrupted()) {
            interrupt();
        }
        if (session.isPresent()) {
            session.get().disconnect();
        }
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            if (!session.isPresent()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    ChannelExec channelExec = (ChannelExec) session.get().openChannel("exec");
                    channelExec.setPty(true);
                    String cmd = "tail -f " + tailModel.getServerLogPath();
                    channelExec.setCommand(cmd);
                    InputStream inputStream = channelExec.getInputStream();
                    channelExec.connect();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                    List<String> buffer = new ArrayList<>();
                    for (int j = 0; j < 3; j++) {//trying 3 times waiting

                        if (bufferedReader.ready()) {
                            while (!isInterrupted()) {

                                String line = bufferedReader.readLine();
                                if (line != null) {
                                    buffer.add(line);
                                    if (buffer.size() >= BUFFER_SIZE) {

                                        logUiQueue.put(Arrays.asList((String[]) buffer.toArray()));
                                        logFileQueue.put(Arrays.asList((String[]) buffer.toArray()));

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
                    //todo
                }
            }
        }
    }
}
