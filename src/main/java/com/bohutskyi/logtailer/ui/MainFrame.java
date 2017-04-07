package com.bohutskyi.logtailer.ui;

import com.bohutskyi.logtailer.service.FormParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * @author Serhii Bohutskyi
 */
public abstract class MainFrame extends javax.swing.JFrame {
    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    private volatile boolean isTailing = false;
    private volatile boolean isTailingToFile = false;

    private SimpleAttributeSet keyWord = new SimpleAttributeSet();
    private Thread logThread;

    @Autowired
    private BlockingQueue<java.util.List<String>> logUiQueue;
    @Autowired
    private ApplicationEventPublisher publisher;

    @PostConstruct
    public void start() {
        handle4Kmonitor();


        logThread = new LogUiWriterThread();
        logThread.start();
    }

    private void handle4Kmonitor() {
        if (is4K()) {
            updateFont(new Font("Arial", Font.PLAIN, 28));
        }
    }

    protected abstract void updateFont(Font font);

    @PreDestroy
    public void stop() {
        if (logThread.isAlive()) {
            logThread.interrupt();
        }
    }

    public Map<FormParameter, String> getParameters() {
        Map<FormParameter, String> params = new HashMap<FormParameter, String>();
        params.put(FormParameter.HOST, getHostTextField().getText());
        params.put(FormParameter.PORT, getPortTextField().getText());
        params.put(FormParameter.USERNAME, getUsernameTextField().getText());
        params.put(FormParameter.PASSWORD, new String(getPasswordPasswordField().getPassword()));
        params.put(FormParameter.SERVER_LOG_PATH, getServerLogPathTextField().getText());
        params.put(FormParameter.LOCAL_LOG_PATH, getLocalLogPathTextField().getText());
        return params;
    }


    private class LogUiWriterThread extends Thread {
        private Logger logger = LoggerFactory.getLogger(getClass().getName());

        public LogUiWriterThread() {
            super("LogUiWriterThread");
        }

        //this thread to update logs on UI
        @Override
        public void run() {
            try {
                while (isInterrupted()) {
                    consume(logUiQueue.take());
                }
            } catch (InterruptedException ex) {
                logger.debug("LogUiWriterThread interrupted!");
            }
        }
    }

    private void consume(java.util.List<String> logs) {
        try {
            for (String log : logs) {
                getLogDocument().insertString(getLogDocument().getLength(), "\n" + log, keyWord);
            }
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
        setEditable(false, true);
    }

    public void stopTailToFileUi() {
        getTailToFileButton().setText("Tail to file");
        isTailingToFile = false;
    }

    public void startTailUi() {
        getTailButton().setText("Stop tailing");
        isTailing = true;
        setEditable(false, false);
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

    public abstract JTextField getLocalLogPathTextField();

    public boolean is4K() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        return width > 3000 && height > 2000;
    }

    public void setEditable(boolean editable, boolean withLocalFilePath) {
        getHostTextField().setEditable(editable);
        getPortTextField().setEditable(editable);
        getUsernameTextField().setEditable(editable);
        getPasswordPasswordField().setEditable(editable);
        getServerLogPathTextField().setEditable(editable);
        if (withLocalFilePath) {
            getLocalLogPathTextField().setEditable(editable);
        }
    }
}
