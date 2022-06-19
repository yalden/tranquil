package com.ycourlee.tranquil.core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Wrap {@link BufferedReader} for quick access file.
 *
 * @author yooonn
 */
public class BufferedReaderWrapper {

    private BufferedReader reader;

    private BufferedReaderWrapper() {
    }

    public BufferedReaderWrapper(String name) {
        File file = new File(name);
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readLine() throws IOException {
        return reader.readLine();
    }

    public int read(char[] buff, int off, int len) throws IOException {
        return reader.read(buff, off, len);
    }

    public int read() throws IOException {
        return reader.read();
    }
}