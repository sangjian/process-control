package cn.ideabuffer.process.condition;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.ContextWrapper;
import cn.ideabuffer.process.block.Block;
import cn.ideabuffer.process.block.BlockWrapper;
import cn.ideabuffer.process.branch.BranchNode;
import cn.ideabuffer.process.rule.Rule;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public class DoWhileConditionNode extends WhileConditionNode {

    public DoWhileConditionNode(Rule rule, BranchNode branch) {
        super(rule, branch);
    }

    @Override
    public boolean doExecute(Context context) throws Exception {
        if(branch == null) {
            return false;
        }

        Block doWhileBlock = new Block(true, true, context.getBlock());
        BlockWrapper blockWrapper = new BlockWrapper(doWhileBlock);
        ContextWrapper doWhileContext = new ContextWrapper(context, doWhileBlock);
        while (true) {
            if(branch.execute(doWhileContext)) {
                return true;
            }
            if (blockWrapper.hasBroken()) {
                break;
            }
            if(!rule.match(context)) {
                break;
            }
            blockWrapper.resetBreak();
            blockWrapper.resetContinue();
        }

        return false;
    }
}
