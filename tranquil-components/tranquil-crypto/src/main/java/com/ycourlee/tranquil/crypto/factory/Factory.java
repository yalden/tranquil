package com.ycourlee.tranquil.crypto.factory;

/**
 * @author yooonn
 * @date 2021.11.29
 */
public interface Factory<T, P> {

    T generate(P obj) throws Exception;
}
