package com.bohutskyi.logtailer.service;

import com.bohutskyi.logtailer.exception.SshClientException;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

/**
 * @author Serhii Bohutksyi
 */
@Component
public class SshClientImpl implements SshClient {

    @Autowired
    private JSch jsch;
    private Session session;

    @Override
    public void connect(SshConfig sshConfig) {
        if (isConnected()) {
            disconnect();
        }
        try {
            Session jschSession = jsch.getSession(sshConfig.getUsername(), sshConfig.getHost(), sshConfig.getPort());
            jschSession.setPassword(sshConfig.getPassword());
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            jschSession.setConfig(config);
            jschSession.connect(15000);
            jschSession.setServerAliveInterval(15000);

            setSession(jschSession);
        } catch (JSchException e) {
            throw new SshClientException("Authentication failed! Check your credentials.", e);
        }
    }

    @Override
    public InputStream readFile(String filePath) {
        if (!isConnected()) {
            throw new IllegalStateException("Ssh client NOT connected, please connect first!");
        }
        try {
            ChannelExec channelExec = (ChannelExec) getSession().get().openChannel("exec");
            channelExec.setPty(true);
            String cmd = "tail -f " + filePath;
            channelExec.setCommand(cmd);
            InputStream inputStream = channelExec.getInputStream();
            channelExec.connect();
            return inputStream;
        } catch (JSchException | IOException e) {
            throw new SshClientException("Cannot start tailing with ssh client!", e);
        }
    }

    @Override
    public boolean isConnected() {
        return getSession().isPresent() && getSession().get().isConnected();
    }

    @PreDestroy
    @Override
    public void disconnect() {
        getSession().ifPresent(Session::disconnect);
    }

    private Optional<Session> getSession() {
        return Optional.ofNullable(session);
    }

    private void setSession(Session session) {
        this.session = session;
    }
}
