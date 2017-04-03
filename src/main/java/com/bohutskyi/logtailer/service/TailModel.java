package com.bohutskyi.logtailer.service;

/**
 * @author Serhii Bohutksyi
 */
public class TailModel {

    private String host;
    private Integer port = 22;
    private String username;
    private String password;
    private String serverLogPath;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getServerLogPath() {
        return serverLogPath;
    }

    public void setServerLogPath(String serverLogPath) {
        this.serverLogPath = serverLogPath;
    }
}
