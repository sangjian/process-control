package cn.ideabuffer.process;

import cn.ideabuffer.process.block.Block;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sangjian.sj
 * @date 2020/02/07
 */
public class DefaultContext extends ConcurrentHashMap<Object, Object> implements Context {

    private static final long serialVersionUID = -6148096437439097786L;
    private Block block;

    public DefaultContext() {
        this(new Block());
    }

    public DefaultContext(Block block) {
        if (block == null) {
            throw new NullPointerException();
        }
        this.block = block;
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public <V> V getValue(Object key) {
        Object value = get(key);
        if (value == null) {
            return null;
        }
        //noinspection unchecked
        return (V)value;
    }

    @Override
    public <V> V get(Object key, V defaultValue) {
        Object value = get(key);
        if (value == null) {
            return defaultValue;
        }
        //noinspection unchecked
        return (V)value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        if (!super.equals(o)) { return false; }
        DefaultContext that = (DefaultContext)o;
        return Objects.equals(block, that.block);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), block);
    }
}
