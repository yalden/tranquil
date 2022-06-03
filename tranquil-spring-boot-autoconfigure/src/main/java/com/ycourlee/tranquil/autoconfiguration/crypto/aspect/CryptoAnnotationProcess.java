package com.ycourlee.tranquil.autoconfiguration.crypto.aspect;

import java.util.List;

/**
 * @author yoooonn
 * @date 2021.12.17
 */
public interface CryptoAnnotationProcess {

    void processInput(Object obj, List<String> enableGroups);
}
