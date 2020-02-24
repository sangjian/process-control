package cn.ideabuffer.process.block;

/**
 * @author sangjian.sj
 * @date 2020/02/06
 */
public class DefaultBlock extends AbstractBlock {
    public DefaultBlock() {
        super();
    }

    public DefaultBlock(Block parent) {
        super(parent);
    }

    public DefaultBlock(boolean breakable, boolean continuable, Block parent) {
        super(breakable, continuable, parent);
    }
}
