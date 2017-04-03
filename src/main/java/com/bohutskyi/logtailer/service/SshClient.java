package com.bohutskyi.logtailer.service;

import com.bohutskyi.logtailer.event.PrintBufferEvent;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;

/**
 * @author Serhii Bohutksyi
 */
@Component
public class SshClient extends Thread {

    @Autowired
    private BlockingQueue<String> logQueue;
    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private JSch jsch;
    private TailModel tailModel;
    private Session session;

    public void connect(TailModel tailModel) throws JSchException {
        this.tailModel = tailModel;

        session = jsch.getSession(tailModel.getUsername(), tailModel.getHost(), tailModel.getPort());
        session.setPassword(tailModel.getPassword());
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect(15000);
        session.setServerAliveInterval(15000);

        start();
    }

    @PreDestroy
    public void disconnect() {
        if (!isInterrupted()) {
            interrupt();
        }
        session.disconnect();
    }

    @Override
    public void run() {
        try {
            ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
            channelExec.setPty(true);
            String cmd = "tail -f " + tailModel.getServerLogPath();
            channelExec.setCommand(cmd);
            InputStream inputStream = channelExec.getInputStream();
            channelExec.connect();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            for (int j = 0; j < 3; j++) {//trying 3 times waiting

                if (bufferedReader.ready()) {
                    while (!isInterrupted()) {

                        String line = bufferedReader.readLine();
                        if (line != null) {
                            logQueue.put(line);
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
