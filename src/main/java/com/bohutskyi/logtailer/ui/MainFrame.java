package com.bohutskyi.logtailer.ui;

import com.bohutskyi.logtailer.service.FormParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * @author Serhii Bohutskyi
 */
public abstract class MainFrame extends javax.swing.JFrame implements Runnable {

    private volatile boolean isTailing = false;
    private volatile boolean isTailingToFile = false;

    private SimpleAttributeSet keyWord = new SimpleAttributeSet();
    private Thread logThread;

    @Autowired
    private BlockingQueue<String> logQueue;
    @Autowired
    private ApplicationEventPublisher publisher;

    @PostConstruct
    public void start() {
        logThread = new Thread(this);
        logThread.start();




        handle4Kmonitor();
    }

    private void handle4Kmonitor() {
        if (is4K()) {
            updateFont(new Font("Arial", Font.PLAIN, 28));
        }
    }

    protected abstract void updateFont(Font font);

    @PreDestroy
    public void stop() {
        logThread.interrupt();
    }

    public Map<FormParameter, String> getParameters() {
        HashMap<FormParameter, String> params = new HashMap<FormParameter, String>();
        params.put(FormParameter.HOST, getHostTextField().getText());
        params.put(FormParameter.PORT, getPortTextField().getText());
        params.put(FormParameter.USERNAME, getUsernameTextField().getText());
        params.put(FormParameter.PASSWORD, new String(getPasswordPasswordField().getPassword()));
        params.put(FormParameter.SERVER_LOG_PATH, getServerLogPathTextField().getText());
        return params;
    }

    //this thread to update logs on UI
    @Override
    public void run() {
        try {
            while (true) {
                consume(logQueue.take());
            }
        } catch (InterruptedException ex) {
            //thread interrupted, todo handle this ?
        }
    }

    void consume(String log) {
        try {
            //todo writetofile
            getLogDocument().insertString(getLogDocument().getLength(), "\n" + log, keyWord);
        } catch (BadLocationException e) {
            e.printStackTrace();//todo
        }
    }

    public boolean isTailing() {
        return isTailing;
    }

    public boolean isTailingToFile() {
        return isTailingToFile;
    }

    public void startTailToFileUi() {
        getTailToFileButton().setText("Stop tailing to file");
        isTailingToFile = true;
    }

    public void stopTailToFileUi() {
        getTailButton().setText("Tail to file");
        isTailingToFile = false;
    }

    public void startTailUi() {
        getTailButton().setText("Stop tailing");
        isTailing = true;
    }

    public void stopTailUi() {
        getTailButton().setText("Tail");
        isTailing = false;
    }

    public abstract JButton getTailButton();

    public abstract JButton getTailToFileButton();

    public abstract JTextField getHostTextField();

    public abstract JTextField getPortTextField();

    public abstract JTextField getUsernameTextField();

    public abstract JPasswordField getPasswordPasswordField();

    public abstract JTextField getServerLogPathTextField();

    public abstract Document getLogDocument();

    public boolean is4K() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        return width > 3000 && height > 2000;
    }
}
