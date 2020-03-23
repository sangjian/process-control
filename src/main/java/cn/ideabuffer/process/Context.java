package cn.ideabuffer.process;

import cn.ideabuffer.process.block.Block;

import java.util.Map;

/**
 * 流程上下文
 *
 * @author sangjian.sj
 * @date 2020/01/18
 */
public interface Context extends Map<Object, Object> {

    /**
     * 获取当前区块
     *
     * @return 当前区块
     */
    Block getBlock();

    /**
     * 获取指定类型的值
     *
     * @param key key
     * @param <V> 返回值类型
     * @return
     */
    <V> V getValue(Object key);

    /**
     * 获取指定key的值，如果为null，则取默认值
     *
     * @param key          key
     * @param defaultValue 默认值
     * @param <V>          返回值类型
     * @return
     */
    <V> V get(Object key, V defaultValue);

    <V> V getResult();

    void setResult(Object result);

}
