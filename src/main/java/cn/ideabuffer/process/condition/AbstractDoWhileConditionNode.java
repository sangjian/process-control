package cn.ideabuffer.process.condition;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.ContextWrapper;
import cn.ideabuffer.process.ExecutableNode;
import cn.ideabuffer.process.block.Block;
import cn.ideabuffer.process.block.BlockWrapper;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public abstract class AbstractDoWhileConditionNode extends AbstractWhileConditionNode {

    public AbstractDoWhileConditionNode(String id) {
        super(id);
    }

    @Override
    public boolean execute(Context context) throws Exception {
        List<ExecutableNode> list = getNodes();
        if (list == null || list.size() == 0) {
            return false;
        }

        Block doWhileBlock = new Block(true, true, context.getBlock());
        BlockWrapper blockWrapper = new BlockWrapper(doWhileBlock);
        ContextWrapper doWhileContext = new ContextWrapper(context, doWhileBlock);
        while (true) {
            if(executeNodes(list, doWhileContext, blockWrapper)) {
                return true;
            }
            if (blockWrapper.hasBroken()) {
                break;
            }
            Boolean judgement = judge(doWhileContext);
            if (!Boolean.TRUE.equals(judgement)) {
                break;
            }
            blockWrapper.resetBreak();
            blockWrapper.resetContinue();
        }

        return false;
    }
}
