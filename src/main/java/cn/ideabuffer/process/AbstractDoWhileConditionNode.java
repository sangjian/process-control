package cn.ideabuffer.process;

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

        Block doWhileBlock = new DefaultBlock(true, true, context.getBlock());
        ContextWrapper whileContext = new ContextWrapper(context, doWhileBlock);
        while (true) {
            boolean hasBreak = false;
            for (ExecutableNode node : list) {
                boolean stop = node.execute(whileContext);
                if (stop) {
                    return true;
                }
                if (doWhileBlock.hasBroken()) {
                    hasBreak = true;
                    break;
                }
                if (doWhileBlock.hasContinued()) {
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
            doWhileBlock.resetBreak();
            doWhileBlock.resetContinue();
        }

        return false;
    }
}
