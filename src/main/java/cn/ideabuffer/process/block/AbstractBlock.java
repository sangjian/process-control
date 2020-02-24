package cn.ideabuffer.process.block;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sangjian.sj
 * @date 2020/02/22
 */
public abstract class AbstractBlock extends ConcurrentHashMap<Object, Object> implements Block {

    protected boolean breakable;

    protected boolean continuable;

    protected boolean hasBroken;

    protected boolean hasContinued;

    protected Block parent;

    public AbstractBlock() {
        this(null);
    }

    public AbstractBlock(Block parent) {
        this(false, false, parent);
    }

    public AbstractBlock(boolean breakable, boolean continuable, Block parent) {
        this.breakable = breakable;
        this.continuable = continuable;
        this.parent = parent;
    }

    @Override
    public <K, V> V get(K key, V defaultValue) {
        return null;
    }

    @Override
    public Block parent() {
        return parent;
    }

    @Override
    public boolean allowBreak() {
        if(breakable) {
            return true;
        }
        if(parent != null) {
            return parent.allowBreak();
        }
        return false;
    }

    @Override
    public boolean allowContinue() {
        if(continuable) {
            return true;
        }
        if(parent != null) {
            return parent.allowContinue();
        }
        return false;
    }

    @Override
    public void doBreak() {
        if(breakable) {
            hasBroken = true;
            return;
        }
        if(!allowBreak()) {
            throw new IllegalStateException();
        }
        if(parent != null) {
            parent.doBreak();
        }
    }

    @Override
    public void doContinue() {
        if(continuable) {
            this.hasContinued = true;
            return;
        }
        if(!allowContinue()) {
            throw new IllegalStateException();
        }
        if(parent != null) {
            parent.doContinue();
        }
    }

    @Override
    public boolean breakable() {
        return breakable;
    }

    @Override
    public boolean continuable() {
        return continuable;
    }
}
