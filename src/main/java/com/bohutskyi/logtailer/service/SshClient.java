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
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * @author Serhii Bohutksyi
 */
@Component
public class SshClient {

    @Autowired
    private ApplicationEventPublisher publisher;

    private JSch jsch;
    private Session session;

    public void connect(TailData tailData) throws JSchException {
        jsch = new JSch();
        session = jsch.getSession(tailData.getUsername(), tailData.getHost(), tailData.getPort());
        session.setPassword(tailData.getPassword());
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect(15000);
        session.setServerAliveInterval(15000);
    }

    @Async
    public void tail(String filePath) throws JSchException, IOException, InterruptedException {
        ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
        channelExec.setPty(true);
        String cmd = "tail -f " + filePath;
        channelExec.setCommand(cmd);
        InputStream inputStream = channelExec.getInputStream();
        channelExec.connect();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        List<String> buffer = new LinkedList<String>();
        for (int j = 0; j < 3; j++) {//trying 3 times waiting

            if (bufferedReader.ready()) {
                while (true) {

                    String line = bufferedReader.readLine();
                    if (line != null) {
                        buffer.add(line);
                        if (buffer.size() >= 10) {
                            publisher.publishEvent(new PrintBufferEvent(buffer));
                            buffer.clear();
                        }
                    } else {
                        Thread.sleep(100);
                    }
                }
            }

            Thread.sleep(1000);
        }
    }
}
