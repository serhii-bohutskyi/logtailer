package com.bohutskyi.logtailer.service;

import java.io.InputStream;

/**
 * @author Serhii Bohutskyi
 */
public interface SshClient {

    public void connect(SshConfig sshConfig);

    public boolean isConnected();

    public void disconnect();

    public InputStream readFile(String filePath);

    public interface SshConfig {
        String getUsername();

        String getPassword();

        String getHost();

        Integer getPort();
    }
}
