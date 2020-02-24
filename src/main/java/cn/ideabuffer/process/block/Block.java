package cn.ideabuffer.process.block;

import java.util.Map;

/**
 * @author sangjian.sj
 * @date 2020/01/31
 */
public interface Block extends Map<Object, Object> {

    <K, V> V get(K key, V defaultValue);

    Block parent();

    boolean allowBreak();

    boolean allowContinue();

    void doBreak();

    void doContinue();

    boolean breakable();

    boolean continuable();
}
