package com.ycourlee.tranquil.core.util;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;

/**
 * @author yoooonn
 */
public class BufferedWriterWrapper implements Closeable {

    private BufferedWriter writer;

    private BufferedWriterWrapper() {
    }

    public BufferedWriterWrapper(String name) throws FileAlreadyExistsException {
        this(name, false);
    }

    public BufferedWriterWrapper(String name, boolean overwriteMode) throws FileAlreadyExistsException {
        File file = new File(name);
        if (file.exists() && !overwriteMode) {
            throw new FileAlreadyExistsException(name);
        }
        try {
            writer = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(byte[] section) throws IOException {
        writer.write(new String(section));
    }

    public void writeLine(byte[] section) throws IOException {
        writer.write(new String(section));
        writer.newLine();
    }

    public void write(String section) throws IOException {
        writer.write(section);
    }

    public void writeLine(String section) throws IOException {
        writer.write(section);
        writer.newLine();
    }

    public void newline() throws IOException {
        writer.newLine();
    }

    public void save() throws IOException {
        close();
    }

    @Override
    public void close() throws IOException {
        writer.flush();
        writer.close();
    }
}