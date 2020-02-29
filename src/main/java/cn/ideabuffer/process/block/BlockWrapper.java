package cn.ideabuffer.process.block;

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
}
