package com.ycourlee.tranquil.core.util;

import com.ycourlee.tranquil.core.UserDirectories;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author yooonn
 * @date 2022.06.17
 */
public class FileUtilTests {

    @Test
    void writeToFileTest() throws IOException {
        String filepath = UserDirectories.TEMP_DIR + "/a.txt";
        FileUtil.rmDirOrFile(filepath);
        assertFalse(FileUtil.exist(filepath));
        FileUtil.writeToFile("foo text", filepath);
        assertTrue(FileUtil.exist(filepath));
        assertEquals("foo text", new BufferedReaderWrapper(filepath).readLine());


        FileUtil.rmDirOrFile(filepath);
        assertFalse(FileUtil.exist(filepath));
        FileUtil.writeToFile("foo text", filepath);
        assertTrue(FileUtil.exist(filepath));
        assertEquals("foo text", new BufferedReaderWrapper(filepath).readLine());
    }

    @Test
    void rmDirOrFileTest() {
        FileUtil.rmDirOrFile(UserDirectories.TEMP_DIR + "/this-is-a-not-exist-file.txt");

        // noinspection ResultOfMethodCallIgnored
        new File(UserDirectories.TEMP_DIR).mkdirs();
        FileUtil.rmDirOrFile(UserDirectories.TEMP_DIR);
    }
}
