package cn.ideabuffer.process;

import cn.ideabuffer.process.block.Block;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sangjian.sj
 * @date 2020/02/07
 */
public class DefaultContext extends ConcurrentHashMap<Object, Object> implements Context {

    private Block block;

    public DefaultContext() {
        this(new Block());
    }

    public DefaultContext(Block block) {
        if(block == null) {
            throw new NullPointerException();
        }
        this.block = block;
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public <V> V get(Object key, Class<V> valueType) {
        Object value = get(key);
        if(value == null) {
            return null;
        }
        //noinspection unchecked
        return (V)value;
    }

    @Override
    public <K, V> V get(K key, V defaultValue) {
        //noinspection unchecked
        V value = (V)get(key);
        if(value != null) {
            return value;
        }
        return defaultValue;
    }
}
