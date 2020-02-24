package cn.ideabuffer.process;

import cn.ideabuffer.process.block.Block;

import java.util.Map;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public interface Context extends Map<Object, Object> {

    Block getBlock();

    <K, V> V get(K key, V defaultValue);

}
