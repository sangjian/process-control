package cn.ideabuffer.process.core.block;

/**
 * @author sangjian.sj
 * @date 2020/02/22
 */
public class BlockWrapper extends Block {

    private Block block;

    public BlockWrapper(Block block) {
        this.block = block;
    }

    @Override
    public boolean breakable() {return block.breakable();}

    @Override
    public boolean continuable() {return block.continuable();}

    @Override
    public boolean hasBroken() {return block.hasBroken();}

    @Override
    public boolean hasContinued() {return block.hasContinued();}

    @Override
    public void resetBreak() {block.resetBreak();}

    @Override
    public void resetContinue() {block.resetContinue();}

    @Override
    public boolean equals(Object o) {return block.equals(o);}

    @Override
    public int hashCode() {return block.hashCode();}
}
