package com.ycourlee.tranquil.core.util;

import com.ycourlee.tranquil.core.CommonConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author yooonn
 * @date 2021.04.20
 */
public class FileUtil {

    private static final Logger log = LoggerFactory.getLogger(FileUtil.class);

    private FileUtil() {}

    public static void writeToFile(String text, String filepath) {
        writeToFile(text.getBytes(StandardCharsets.UTF_8), filepath);
    }

    public static void writeToFile(byte[] buffer, String filepath) {
        File file = new File(filepath);
        mkdirIfNotExist(file);
        FileOutputStream fileOutputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            int idx = 0;
            while (idx < buffer.length) {
                int len = Math.min(buffer.length - idx, CommonConstants.BUFFER);
                bufferedOutputStream.write(buffer, idx, len);
                idx += len;
            }
            bufferedOutputStream.flush();
        } catch (IOException e) {
            log.error("write to file error, filepath: {}", filepath, e);
        } finally {
            try {
                if (bufferedOutputStream != null) {
                    bufferedOutputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                log.error("IO close error, filepath: {}", filepath, e);
            }
        }
    }

    private static void mkdirIfNotExist(File file) {
        if (!file.exists()) {
            // noinspection ResultOfMethodCallIgnored
            file.getParentFile().mkdirs();
        }
    }

    public static void rmDirOrFile(String filepath) {
        File file = new File(filepath);
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            // noinspection ResultOfMethodCallIgnored
            file.delete();
            log.trace("delete a file, filepath: {}", filepath);
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            log.trace("delete all files in directory: {}, they are output in log", filepath);
            if (files == null) {
                return;
            }
            for (File f : files) {
                rmDirOrFile(f.getAbsolutePath());
            }
            // noinspection ResultOfMethodCallIgnored
            file.delete();
            log.trace("delete the directory at last");
        }
    }

    public static boolean exist(String filepath) {
        return new File(filepath).exists();
    }
}
