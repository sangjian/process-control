package cn.ideabuffer.process.core.context;

import cn.ideabuffer.process.core.block.Block;
import cn.ideabuffer.process.core.block.BlockFacade;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;

/**
 * 流程上下文的实现。
 *
 * @author sangjian.sj
 * @date 2020/02/07
 */
public class ContextImpl extends ParameterImpl implements Context {

    /**
     * 当前block
     */
    private Block block;

    private Exception exception;

    public ContextImpl() {
        this(null, null);
    }

    public ContextImpl(@Nullable Block block) {
        this(block, null);
    }

    public ContextImpl(@Nullable Block block, @Nullable Map<Key<?>, Object> params) {
        super(params);
        this.block = block == null ? new BlockFacade() : block;
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public Context cloneContext() {
        return new ContextImpl(null, getParameters());
    }

    @Override
    public boolean readableKey(Key<?> key) {
        return false;
    }

    @Override
    public boolean writableKey(Key<?> key) {
        return false;
    }

    @Override
    public Exception getCurrentException() {
        return this.exception;
    }

    @Override
    public void setCurrentException(Exception e) {
        this.exception = e;
    }

    @Override
    public KeyMapper getMapper() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        ContextImpl that = (ContextImpl)o;
        return Objects.equals(block, that.block);
    }

    @Override
    public int hashCode() {

        return Objects.hash(block);
    }
}
