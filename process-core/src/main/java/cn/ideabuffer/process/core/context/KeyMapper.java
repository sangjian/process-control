package cn.ideabuffer.process.core.context;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 参数映射器，解决节点复用的问题。 如果一个节点在一个流程中使用的key的名称为'oldKey'，新流程想复用这个节点， 但对应的key的名称为'newKey'，这时可以通过添加映射来解决参数名称不一致的问题。
 * <pre>
 *                                           ┌—————————————————————————————┐
 *                                           |            Mapper           |
 *                                           |-----------------------------|
 *       ┌—————————————————————————┐         | ┌————————┐       ┌————————┐ |
 *       |   get value by 'oldKey' |---------+>| oldKey |------>| newKey |-+--------┐
 *       └—————————————————————————┘         | └————————┘       └————————┘ |        |
 *                                           |      .               .      |        |
 *                                           |      .               .      |        |
 *                                           |      .               .      |        |
 *                                           |                             |        |
 *                                           └—————————————————————————————┘        |
 *                                  ┌-----------------------------------------------┘
 *                                  |        ┌—————————————————————————————┐
 *                                  |        |        ParameterMap         |
 *                                  |        |-----------------------------|
 *                                  |        | ┌————————┐       ┌————————┐ |
 *                                  └--------+>| newKey |------>|  value | |
 *                                           | └————————┘       └————————┘ |
 *                                           |      .               .      |
 *                                           |      .               .      |
 *                                           |      .               .      |
 *                                           |                             |
 *                                           └————————————————————————---——┘
 *  </pre>
 *
 * @author sangjian.sj
 * @date 2020/05/20
 */
public class KeyMapper {

    private Map<Key<?>, Key<?>> mapper;

    private KeyMapper parent;

    public KeyMapper() {
        this(null);
    }

    public KeyMapper(KeyMapper parent) {
        this.mapper = new ConcurrentHashMap<>();
        this.parent = parent;
    }

    /**
     * 参数key映射，将原有的key：from映射至新的key：to
     *
     * @param oldKey 原有key
     * @param newKey 待映射的key
     * @param <V>    值类型
     */
    public <V> void map(@NotNull Key<V> oldKey, @NotNull Key<V> newKey) {
        this.mapper.put(oldKey, newKey);
    }

    /**
     * 获取key的映射。
     *
     * @param key 待获取映射的key
     * @param <V> 值类型
     * @return 如果有映射，返回映射的key，否则返回null
     */
    public <V> Key<V> getMappedKey(@NotNull Key<V> key) {
        Key<V> mappedKey = key;
        KeyMapper p = this;
        // 逐级查找，最终找到原始key
        do {
            //noinspection unchecked
            mappedKey = (Key<V>)p.mapper.get(mappedKey);
            p = p.parent;
        } while (p != null);
        return mappedKey;
    }

    public boolean isEmpty() {
        return this.mapper.isEmpty();
    }
}
