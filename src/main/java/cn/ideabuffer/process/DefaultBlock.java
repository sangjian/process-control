package cn.ideabuffer.process;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sangjian.sj
 * @date 2020/02/06
 */
public class DefaultBlock extends ConcurrentHashMap<Object, Object> implements Block {

    private boolean breakable;

    private boolean continuable;

    protected boolean hasBroken;

    protected boolean hasContinued;

    private Block parent;

    public DefaultBlock() {
        this(null);
    }

    public DefaultBlock(Block parent) {
        this(false, false, parent);
    }

    public DefaultBlock(boolean breakable, boolean continuable, Block parent) {
        this.breakable = breakable;
        this.continuable = continuable;
        this.parent = parent;
    }

    @Override
    public <K, V> V get(K key, V defaultValue) {
        V value = (V)get(key);
        if(value != null) {
            return value;
        }
        return defaultValue;
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
    public boolean breakable() {
        return breakable;
    }

    @Override
    public boolean continuable() {
        return continuable;
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
    public boolean hasBroken() {
        if(hasBroken) {
            return true;
        }
        if(parent != null) {
            return parent.hasBroken();
        }
        return false;
    }

    @Override
    public boolean hasContinued() {
        if(hasContinued) {
            return true;
        }
        if(parent != null) {
            return parent.hasBroken();
        }
        return false;
    }

    @Override
    public void resetBreak() {
        this.hasBroken = false;
    }

    @Override
    public void resetContinue() {
        this.hasContinued = false;
    }
}
