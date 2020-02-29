package cn.ideabuffer.process.block;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sangjian.sj
 * @date 2020/02/22
 */
public class Block extends ConcurrentHashMap<Object, Object> {

    private boolean breakable;

    private boolean continuable;

    private boolean hasBroken;

    private boolean hasContinued;

    private Block parent;

    public Block() {
        this(null);
    }

    public Block(Block parent) {
        this(false, false, parent);
    }

    public Block(boolean breakable, boolean continuable, Block parent) {
        this.breakable = breakable;
        this.continuable = continuable;
        this.parent = parent;
    }

    public <K, V> V get(K key, V defaultValue) {
        return null;
    }

    public Block parent() {
        return parent;
    }

    public boolean allowBreak() {
        if(breakable) {
            return true;
        }
        if(parent != null) {
            return parent.allowBreak();
        }
        return false;
    }

    public boolean allowContinue() {
        if(continuable) {
            return true;
        }
        if(parent != null) {
            return parent.allowContinue();
        }
        return false;
    }

    public void doBreak() {
        if(breakable) {
            hasBroken = true;
            return;
        }
        if(!allowBreak()) {
            throw new IllegalStateException();
        }
        Block p = parent;
        while(p != null) {
            p.hasBroken = true;
            if(p.breakable) {
                break;
            }
            p = p.parent;
        }
    }

    public void doContinue() {
        if(continuable) {
            this.hasContinued = true;
            return;
        }
        if(!allowContinue()) {
            throw new IllegalStateException();
        }
        Block p = parent;
        while(p != null) {
            p.hasContinued = true;
            if(p.continuable) {
                break;
            }
            p = p.parent;
        }
    }

    boolean breakable() {
        return breakable;
    }

    boolean continuable() {
        return continuable;
    }

    boolean hasBroken() {
        return hasBroken;
    }

    boolean hasContinued() {
        return hasContinued;
    }

    void resetBreak() {
        this.hasBroken = false;
    }

    void resetContinue() {
        this.hasContinued = false;
    }
}
