package com.ycourlee.tranquil.core;

/**
 * @author yooonn
 * @date 2021.11.29
 */
public interface Factory<T, P> {

    /**
     * generate Object by Param obj.
     *
     * @param obj param
     * @return object
     * @throws Exception e
     */
    T generate(P obj) throws Exception;
}
