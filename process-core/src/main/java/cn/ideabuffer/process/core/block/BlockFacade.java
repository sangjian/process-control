package cn.ideabuffer.process.core.block;

import cn.ideabuffer.process.core.context.KeyMapper;
import org.jetbrains.annotations.Nullable;

/**
 * {@link Block}对象的一个包装，增加了key映射的功能。
 *
 * @author sangjian.sj
 * @date 2020/02/22
 * @see KeyMapper
 */
public class BlockFacade implements Block {

    /**
     * 被包装的block
     */
    private Block block;

    public BlockFacade() {
        this(null);
    }

    public BlockFacade(@Nullable Block block) {
        this.block = block == null ? new InnerBlock() : block;
    }

    @Override
    public boolean allowBreak() {
        return block.allowBreak();
    }

    @Override
    public boolean allowContinue() {
        return block.allowContinue();
    }

    @Override
    public void doBreak() {
        block.doBreak();
    }

    @Override
    public void doContinue() {
        block.doContinue();
    }

    @Override
    public boolean hasBroken() {
        return block.hasBroken();
    }

    @Override
    public boolean hasContinued() {
        return block.hasContinued();
    }

    @Override
    public Block getParent() {
        return block.getParent();
    }

}
