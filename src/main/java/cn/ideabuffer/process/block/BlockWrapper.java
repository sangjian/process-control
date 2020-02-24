package cn.ideabuffer.process.block;

/**
 * @author sangjian.sj
 * @date 2020/02/22
 */
public class BlockWrapper extends BlockFacade {


    protected boolean hasBroken;

    protected boolean hasContinued;

    public BlockWrapper(Block block) {
        super(block);
    }

    @Override
    public void doBreak() {
        if (breakable()) {
            hasBroken = true;
            return;
        }
        if (!allowBreak()) {
            throw new IllegalStateException();
        }
        if (parent() != null) {
            parent().doBreak();
        }
    }

    @Override
    public void doContinue() {
        if (continuable()) {
            hasContinued = true;
            return;
        }
        if (!allowContinue()) {
            throw new IllegalStateException();
        }
        if (parent() != null) {
            parent().doContinue();
        }
    }

    public boolean hasBroken() {
        return hasBroken;
    }

    public boolean hasContinued() {
        return hasContinued;
    }

    public void resetBreak() {
        this.hasBroken = false;
    }

    public void resetContinue() {
        this.hasContinued = false;
    }
}
