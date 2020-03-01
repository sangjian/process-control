package cn.ideabuffer.process.branch;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.ExecutableNode;
import cn.ideabuffer.process.block.BlockWrapper;
import cn.ideabuffer.process.nodes.AbstractExecutableNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static cn.ideabuffer.process.executor.ExecuteStrategies.SERIAL;

/**
 * @author sangjian.sj
 * @date 2020/03/01
 */
public class DefaultBranch extends AbstractExecutableNode implements Branch {

    private List<ExecutableNode> nodes;

    public DefaultBranch() {
        this(null, null);
    }

    public DefaultBranch(ExecutableNode... nodes) {
        this(nodes == null ? null : Arrays.asList(nodes));
    }

    public DefaultBranch(List<ExecutableNode> nodes) {
        this(null, nodes);
    }

    public DefaultBranch(String id, List<ExecutableNode> nodes) {
        super(id);
        this.nodes = nodes == null ? new ArrayList<>() : nodes;
    }

    @Override
    public DefaultBranch addNodes(ExecutableNode... nodes) {
        if(nodes != null && nodes.length > 0) {
            this.nodes.addAll(new ArrayList<>(Arrays.asList(nodes)));
        }
        return this;
    }

    @Override
    public List<ExecutableNode> getNodes() {
        return nodes;
    }

    @Override
    public boolean execute(Context context) throws Exception {
        return doExecute(context);
    }

    @Override
    protected boolean doExecute(Context context) throws Exception {

        ExecutableNode[] executableNodes = new ExecutableNode[nodes.size()];

        return SERIAL.execute(getExecutor(), context, nodes.toArray(executableNodes));
    }
}
