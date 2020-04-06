package cn.ideabuffer.process.core.block;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.ParameterImpl;

import java.util.Objects;

/**
 * 用于表示一组节点所在的范围，通过Context获取，同一block内，数据可共享，与当前context数据隔离
 *
 * @author sangjian.sj
 * @date 2020/02/22
 * @see Context#getBlock()
 */
public class Block extends ParameterImpl {

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
        if (parent != null) {
            this.putAll(parent.getParams());
        }
    }

    public boolean allowBreak() {
        if (breakable) {
            return true;
        }
        if (parent != null) {
            return parent.allowBreak();
        }
        return false;
    }

    public boolean allowContinue() {
        if (continuable) {
            return true;
        }
        if (parent != null) {
            return parent.allowContinue();
        }
        return false;
    }

    public void doBreak() {
        if (breakable) {
            hasBroken = true;
            return;
        }
        if (!allowBreak()) {
            throw new IllegalStateException();
        }
        Block p = parent;
        while (p != null) {
            p.hasBroken = true;
            if (p.breakable) {
                break;
            }
            p = p.parent;
        }
    }

    public void doContinue() {
        if (continuable) {
            this.hasContinued = true;
            return;
        }
        if (!allowContinue()) {
            throw new IllegalStateException();
        }
        Block p = parent;
        while (p != null) {
            p.hasContinued = true;
            if (p.continuable) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        if (!super.equals(o)) { return false; }
        Block block = (Block)o;
        return breakable == block.breakable &&
            continuable == block.continuable &&
            hasBroken == block.hasBroken &&
            hasContinued == block.hasContinued &&
            Objects.equals(parent, block.parent);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), breakable, continuable, hasBroken, hasContinued, parent);
    }
}
