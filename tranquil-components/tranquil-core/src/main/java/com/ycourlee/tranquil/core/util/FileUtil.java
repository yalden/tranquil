package com.ycourlee.tranquil.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author yongjiang
 * @date 2021.04.20
 */
public class FileUtil {

    private static final Logger log = LoggerFactory.getLogger(FileUtil.class);

    public void writeToFile(byte[] buffer, String filepath) {
        File file = new File(filepath);
        FileOutputStream fileOutputStream = null;

        BufferedOutputStream bufferedOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            bufferedOutputStream.write(buffer, 0, buffer.length);
            bufferedOutputStream.flush();

        } catch (IOException e) {
            log.error("写入失败, filepath = {}, e = {}", filepath, e.getMessage());
        } finally {
            try {
                if (bufferedOutputStream != null) {
                    bufferedOutputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();

                }
            } catch (IOException e) {
                log.error("IO关闭失败, e = {}", e.getMessage());
            }
        }
    }

    public void rmDirOrFile(String filepath) {
        File file = new File(filepath);
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            file.delete();
            log.info("delete a file! filepath = {}", filepath);
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            log.info("delete all files in directory = {}, they are output in log", filepath);
            for (File f : files) {
                rmDirOrFile(f.getAbsolutePath());
            }
            file.delete();
            log.info("delete the directory at last");
        }
    }
}
