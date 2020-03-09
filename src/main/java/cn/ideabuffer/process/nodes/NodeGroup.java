package cn.ideabuffer.process.nodes;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.ContextWrapper;
import cn.ideabuffer.process.block.Block;
import cn.ideabuffer.process.nodes.branch.DefaultBranch;

/**
 * @author sangjian.sj
 * @date 2020/03/01
 */
public class NodeGroup extends DefaultBranch {

    @Override
    public boolean execute(Context context) throws Exception {
        Block block = new Block(context.getBlock());
        ContextWrapper contextWrapper = new ContextWrapper(context, block);
        return super.execute(contextWrapper);
    }

}
