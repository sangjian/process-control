package cn.ideabuffer.process;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public abstract class AbstractWhileConditionNode extends AbstractNode implements WhileConditionNode {

    private List<ExecutableNode> nodes;

    public AbstractWhileConditionNode(String id) {
        super(id);
        nodes = new ArrayList<>();
    }

    @Override
    public WhileConditionNode addNode(ExecutableNode node) {
        if (node == null) {
            throw new NullPointerException();
        }
        nodes.add(node);
        return this;
    }

    @Override
    public List<ExecutableNode> getNodes() {
        return nodes;
    }

    @Override
    public boolean execute(Context context) throws Exception {
        List<ExecutableNode> list = getNodes();
        if (list == null || list.size() == 0) {
            return false;
        }

        Block whileBlock = new DefaultBlock(context.getBlock());
        ContextWrapper whileContext = new ContextWrapper(context, whileBlock);
        while (true) {
            Boolean judgement = judge(whileContext);
            if (!Boolean.TRUE.equals(judgement)) {
                break;
            }
            whileBlock.resetBreak();
            whileBlock.resetContinue();
            boolean hasBreak = false;
            for (ExecutableNode node : list) {
                boolean stop = node.execute(whileContext);
                if (stop) {
                    return true;
                }
                if (whileBlock.hasBroken()) {
                    hasBreak = true;
                    break;
                }
                if (whileBlock.hasContinued()) {
                    break;
                }
            }
            if (hasBreak) {
                break;
            }
        }

        return false;
    }
}
