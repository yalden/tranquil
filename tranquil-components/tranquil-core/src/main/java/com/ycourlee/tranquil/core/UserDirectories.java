package com.ycourlee.tranquil.core;

/**
 * @author yooonn
 * @date 2021.04.20
 */
public class UserDirectories {

    public static final String DOC_DIR = System.getProperty("user.dir") + "/doc";

    public static final String TEMP_DIR               = DOC_DIR + "/temp";
    public static final String RESOURCE_DIR           = DOC_DIR + "/resource";
    public static final String TEMP_JSON_FILE_DIR     = DOC_DIR + "/temp" + "/json";
    public static final String RESOURCE_JSON_FILE_DIR = DOC_DIR + "/resource" + "/json";
}
