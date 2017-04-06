package com.bohutskyi.logtailer.service;

import com.bohutskyi.logtailer.exception.LogFileWriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;

/**
 * @author Serhii Bohutskyi
 */
@Component
public class BufferedFileWriter extends Thread {

    @Autowired
    @Qualifier("logFileQueue")
    private BlockingQueue<List<String>> logFileQueue;

    private FileChannel fileChannel;

    public void startWrite(String filePath) {
        FileChannel channel = openChannel(filePath);
        setFileChannel(channel);
        start();
    }

    @PreDestroy
    public void stopWrite() {
        closeChannel();
        interrupt();
    }

    private FileChannel openChannel(String filePath) {
        File file = new File(filePath);
//        if (!file.exists()) {
//            file.mkdir();
//        }
        try {
            RandomAccessFile accessFile = new RandomAccessFile(file, "rw");
            accessFile.seek(0);
            accessFile.setLength(0);
            return accessFile.getChannel();
        } catch (FileNotFoundException e) {
            throw new LogFileWriterException("Local file not found! Please check path and permissions.");
        } catch (IOException e) {
            throw new LogFileWriterException("Cannot clean Local file! Please check is it locked?");
        }
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                writeToFile(logFileQueue.take());
            }
        } catch (InterruptedException | IOException ex) {
            ex.printStackTrace();
            //thread interrupted, todo handle this
        }
    }

    public void writeToFile(List<String> lines) throws IOException {
        if (!getFileChannel().isPresent() || !getFileChannel().get().isOpen()) {
            throw new IllegalStateException("File Channel is closed or not opened!");
        }
        for (String line : lines) {
            getFileChannel().get().write(ByteBuffer.wrap(("\n" + line).getBytes()));
        }
    }


    private void closeChannel() {
        if (getFileChannel().isPresent() && getFileChannel().get().isOpen()) {
            try {
                getFileChannel().get().close();
            } catch (IOException e) {
                e.printStackTrace();//todo
            }
        }
    }

    public Optional<FileChannel> getFileChannel() {
        return Optional.ofNullable(fileChannel);
    }

    public void setFileChannel(FileChannel fileChannel) {
        this.fileChannel = fileChannel;
    }
}
