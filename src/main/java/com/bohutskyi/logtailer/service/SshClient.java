package com.bohutskyi.logtailer.service;

import com.bohutskyi.logtailer.exception.SshClientException;
import com.bohutskyi.logtailer.model.TailModel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.*;

/**
 * @author Serhii Bohutksyi
 */
@Component
public class SshClient {

    @Autowired
    private JSch jsch;
    private Session session;

    public void connect(TailModel tailModel) {
        if (isConnected()) {
            disconnect();
        }
        try {
            Session jschSession = jsch.getSession(tailModel.getUsername(), tailModel.getHost(), tailModel.getPort());
            jschSession.setPassword(tailModel.getPassword());
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

    public boolean isConnected() {
        return getSession().isPresent() && getSession().get().isConnected();
    }

    @PreDestroy
    public void disconnect() {
        getSession().ifPresent(Session::disconnect);
    }

    public Optional<Session> getSession() {
        return Optional.ofNullable(session);
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
