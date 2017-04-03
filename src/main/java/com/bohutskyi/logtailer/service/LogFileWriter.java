package com.bohutskyi.logtailer.service;

import org.springframework.beans.factory.annotation.Autowired;
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
public class LogFileWriter extends Thread {
    @Autowired
    private BlockingQueue<List<String>> logFileQueue;

    private Optional<FileChannel> fileChannel = Optional.empty();

    public void openChannel(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        fileChannel = Optional.of(new RandomAccessFile(file, "w").getChannel());
    }

    public void writeToFile(List<String> lines) throws IOException {
        if (!fileChannel.isPresent() || !fileChannel.get().isOpen()) {
            throw new IllegalStateException("File Channel is closed or not opened!");
        }
        for (String line : lines) {
            fileChannel.get().write(ByteBuffer.wrap(line.getBytes()));
        }
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                writeToFile(logFileQueue.take());
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
            //thread interrupted, todo handle this ?
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void closeChannel() {
        if (fileChannel.isPresent() && fileChannel.get().isOpen()) {
            try {
                fileChannel.get().close();
            } catch (IOException e) {
                e.printStackTrace();//todo
            }
        }
    }
}
