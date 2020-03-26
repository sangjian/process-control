package cn.ideabuffer.process.nodes;

import cn.ideabuffer.process.context.Context;
import cn.ideabuffer.process.context.ContextWrapper;
import cn.ideabuffer.process.context.Contexts;
import cn.ideabuffer.process.block.Block;
import cn.ideabuffer.process.nodes.branch.DefaultBranchNode;
import cn.ideabuffer.process.status.ProcessStatus;

/**
 * @author sangjian.sj
 * @date 2020/03/01
 */
public class NodeGroup extends DefaultBranchNode {

    @Override
    public ProcessStatus execute(Context context) throws Exception {
        Block block = new Block(context.getBlock());
        ContextWrapper contextWrapper = Contexts.wrap(context, block);
        return super.execute(contextWrapper);
    }

}
