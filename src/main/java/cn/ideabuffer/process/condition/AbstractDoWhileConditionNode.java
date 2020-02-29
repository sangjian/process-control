package cn.ideabuffer.process.condition;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.ContextWrapper;
import cn.ideabuffer.process.ExecutableNode;
import cn.ideabuffer.process.block.Block;
import cn.ideabuffer.process.block.BlockWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public abstract class AbstractDoWhileConditionNode extends AbstractWhileConditionNode {

    private List<ExecutableNode> nodes;

    public AbstractDoWhileConditionNode(String id) {
        super(id);
        nodes = new ArrayList<>();
    }

    @Override
    public boolean execute(Context context) throws Exception {
        List<ExecutableNode> list = getNodes();
        if (list == null || list.size() == 0) {
            return false;
        }

        Block doWhileBlock = new Block(true, true, context.getBlock());
        BlockWrapper blockWrapper = new BlockWrapper(doWhileBlock);
        ContextWrapper whileContext = new ContextWrapper(context, doWhileBlock);
        while (true) {
            boolean hasBreak = false;
            for (ExecutableNode node : list) {
                boolean stop = node.execute(whileContext);
                if (stop) {
                    return true;
                }
                if (blockWrapper.hasBroken()) {
                    hasBreak = true;
                    break;
                }
                if (blockWrapper.hasContinued()) {
                    break;
                }
            }
            if (hasBreak) {
                break;
            }
            Boolean judgement = judge(whileContext);
            if (!Boolean.TRUE.equals(judgement)) {
                break;
            }
            blockWrapper.resetBreak();
            blockWrapper.resetContinue();
        }

        return false;
    }
}
