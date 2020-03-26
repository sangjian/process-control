package cn.ideabuffer.process.context;

import cn.ideabuffer.process.block.Block;

import java.util.Map;
import java.util.Objects;

/**
 * @author sangjian.sj
 * @date 2020/02/07
 */
public class DefaultContext extends ParameterImpl implements Context {

    private Block block;

    public DefaultContext() {
        this(null, null);
    }

    public DefaultContext(Block block) {
        this(block, null);
    }

    public DefaultContext(Block block, Map<Key<?>, Object> params) {
        super(params);
        this.block = block == null ? new Block() : block;
    }

    @Override
    public Block getBlock() {
        return block;
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
