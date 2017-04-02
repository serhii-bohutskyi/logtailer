package com.bohutskyi.logtailer.service;

import com.jcraft.jsch.*;

import java.io.*;
import java.util.Hashtable;
import java.util.Properties;

/**
 * @author Serhii Bohutskyi
 */
public class Application {

  static String user = "remotedeploy";
  static String host = "10.160.7.240";
  static String password = "password";
  static String rfile = "/datalex/logs/jboss/matrixtdp.log";

  public static void main(String[] args) throws Exception {
    JSch jsch = new JSch();
    Session session = jsch.getSession(user, host);
    session.setPassword(password);
    Properties config = new Properties();
    config.put("StrictHostKeyChecking", "no");
    session.setConfig(config);
    session.connect(15000);
    session.setServerAliveInterval(15000);

    ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
    channelExec.setPty(true);
    String cmd = "tail -f " + rfile;
    channelExec.setCommand(cmd);
    InputStream inputStream = channelExec.getInputStream();
    channelExec.connect();
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

    for (int j = 0; j < 3; j++) {

      if (bufferedReader.ready()) {
        for (int i = 0; i < 10000; i++) {

          String line = bufferedReader.readLine();
          if (line != null) {
            System.out.println(line);
          } else {
            Thread.sleep(500);
          }
        }
      }
      Thread.sleep(1000);

    }

    bufferedReader.close();
    channelExec.sendSignal("SIGINT");
    channelExec.disconnect();
    session.disconnect();
    System.out.println("exit");
  }

}
