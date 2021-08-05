package cn.ideabuffer.process.core;

import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.context.KeyMapper;

import java.util.Set;

/**
 * @author sangjian.sj
 * @date 2021/07/10
 */
public interface KeyManager {

    KeyMapper getKeyMapper();

    void setKeyMapper(KeyMapper mapper);

    /**
     * 获取可读key集合
     *
     * @return 可读key集合
     * @see Key
     */
    Set<Key<?>> getReadableKeys();

    /**
     * 设置可读key集合
     *
     * @see Key
     */
    void setReadableKeys(Set<Key<?>> keys);

    /**
     * 获取可写key集合
     *
     * @return 可写key集合
     * @see Key
     */
    Set<Key<?>> getWritableKeys();

    /**
     * 设置可写key集合
     *
     * @see Key
     */
    void setWritableKeys(Set<Key<?>> keys);
}
