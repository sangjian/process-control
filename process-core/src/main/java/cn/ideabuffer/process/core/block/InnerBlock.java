package cn.ideabuffer.process.core.block;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.ParameterImpl;
import cn.ideabuffer.process.core.nodes.condition.IfConditionNode;
import cn.ideabuffer.process.core.nodes.condition.WhileConditionNode;

import java.util.Objects;

/**
 * 内部使用的{@link Block}，用于在进入需要增加作用域的节点（例如{@link WhileConditionNode}，{@link IfConditionNode}等）时，会构建此对象来实现新的作用域。
 *
 * @author sangjian.sj
 * @date 2020/02/22
 * @see Context#getBlock()
 */
public class InnerBlock extends ParameterImpl implements Block {

    private boolean breakable;

    private boolean continuable;

    private boolean hasBroken;

    private boolean hasContinued;

    private Block parent;

    public InnerBlock() {
        this(null);
    }

    public InnerBlock(Block parent) {
        this(false, false, parent);
    }

    public InnerBlock(boolean breakable, boolean continuable, Block parent) {
        this.breakable = breakable;
        this.continuable = continuable;
        this.parent = parent;
        if (parent != null) {
            this.putAll(parent.getParams());
        }
    }

    @Override
    public boolean allowBreak() {
        if (breakable) {
            return true;
        }
        if (parent != null) {
            return parent.allowBreak();
        }
        return false;
    }

    @Override
    public boolean allowContinue() {
        if (continuable) {
            return true;
        }
        if (parent != null) {
            return parent.allowContinue();
        }
        return false;
    }

    @Override
    public void doBreak() {
        if (!allowBreak()) {
            throw new IllegalStateException("break is not allowed in current block");
        }
        hasBroken = true;
        if (breakable) {
            return;
        }
        Block p = getParent();
        if (p != null) {
            p.doBreak();
        }
    }

    @Override
    public void doContinue() {
        if (!allowContinue()) {
            throw new IllegalStateException("continue is not allowed in current block");
        }
        hasContinued = true;
        if (continuable) {
            return;
        }
        Block p = getParent();
        if (p != null) {
            p.doContinue();
        }
    }

    public boolean breakable() {
        return breakable;
    }

    public boolean continuable() {
        return continuable;
    }

    @Override
    public boolean hasBroken() {
        return hasBroken;
    }

    @Override
    public boolean hasContinued() {
        return hasContinued;
    }

    public void resetBreak() {
        this.hasBroken = false;
    }

    public void resetContinue() {
        this.hasContinued = false;
    }

    @Override
    public Block getParent() {
        return parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        InnerBlock that = (InnerBlock)o;
        return breakable == that.breakable &&
            continuable == that.continuable &&
            hasBroken == that.hasBroken &&
            hasContinued == that.hasContinued &&
            Objects.equals(parent, that.parent);
    }

    @Override
    public int hashCode() {

        return Objects.hash(breakable, continuable, hasBroken, hasContinued, parent);
    }
}
