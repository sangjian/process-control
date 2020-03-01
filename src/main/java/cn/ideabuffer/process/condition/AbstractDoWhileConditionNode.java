package cn.ideabuffer.process.condition;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.ContextWrapper;
import cn.ideabuffer.process.ExecutableNode;
import cn.ideabuffer.process.block.Block;
import cn.ideabuffer.process.block.BlockWrapper;

import java.util.List;

import static cn.ideabuffer.process.executor.ExecuteStrategies.SERIAL;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public abstract class AbstractDoWhileConditionNode extends AbstractWhileConditionNode {

    public AbstractDoWhileConditionNode(String id) {
        super(id);
    }

    @Override
    public boolean doExecute(Context context) throws Exception {
        if(getBranch() == null) {
            return false;
        }
        List<ExecutableNode> list = getBranch().getNodes();
        if (list == null || list.isEmpty()) {
            return false;
        }

        Block doWhileBlock = new Block(true, true, context.getBlock());
        BlockWrapper blockWrapper = new BlockWrapper(doWhileBlock);
        ContextWrapper doWhileContext = new ContextWrapper(context, doWhileBlock);
        while (true) {
            if(getBranch().execute(doWhileContext)) {
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
