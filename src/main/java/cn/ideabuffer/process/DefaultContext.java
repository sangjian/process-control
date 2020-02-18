package cn.ideabuffer.process;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sangjian.sj
 * @date 2020/02/07
 */
public class DefaultContext extends ConcurrentHashMap<Object, Object> implements Context {

    private Block block;

    public DefaultContext() {
        this(new DefaultBlock());
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
    public <K, V> V get(K key, V defaultValue) {
        V value = (V)get(key);
        if(value != null) {
            return value;
        }
        return defaultValue;
    }
}
