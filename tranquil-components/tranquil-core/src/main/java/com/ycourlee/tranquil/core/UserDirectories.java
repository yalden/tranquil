package com.ycourlee.tranquil.core;

/**
 * @author yoooonn
 * @date 2021.04.20
 */
public class UserDirectories {

    public static final String DOC_DIR = System.getProperty("user.dir") + "/doc";

    protected static final String TEMP_DIR               = DOC_DIR + "/temp";
    protected static final String RESOURCE_DIR           = DOC_DIR + "/resource";
    protected static final String TEMP_JSON_FILE_DIR     = DOC_DIR + "/temp" + "/json";
    protected static final String RESOURCE_JSON_FILE_DIR = DOC_DIR + "/resource" + "/json";
}
