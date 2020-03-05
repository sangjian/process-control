package cn.ideabuffer.process.branch;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.ExecutableNode;
import cn.ideabuffer.process.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.rule.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static cn.ideabuffer.process.executor.ExecuteStrategies.SERIAL_PROCEEDED;

/**
 * @author sangjian.sj
 * @date 2020/03/01
 */
public class DefaultBranch extends AbstractExecutableNode implements Branch {

    private List<ExecutableNode> nodes;

    private Rule rule;

    public DefaultBranch() {
        this.nodes = new ArrayList<>();
    }

    public DefaultBranch(ExecutableNode... nodes) {
        this(null, nodes);
    }

    public DefaultBranch(List<ExecutableNode> nodes) {
        this.nodes = new ArrayList<>();
        if(nodes != null) {
            this.nodes.addAll(nodes);
        }
    }

    public DefaultBranch(Rule rule, List<ExecutableNode> nodes) {
        this.rule = rule;
        this.nodes = new ArrayList<>();
        if(nodes != null) {
            this.nodes.addAll(nodes);
        }
    }

    public DefaultBranch(Rule rule, ExecutableNode... nodes) {
        this.rule = rule;
        this.nodes = new ArrayList<>();
        if(nodes != null && nodes.length > 0) {
            this.nodes.addAll(Arrays.asList(nodes));
        }
    }

    public void setNodes(List<ExecutableNode> nodes) {
        this.nodes = nodes;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    @Override
    public DefaultBranch addNodes(@NotNull ExecutableNode... nodes) {
        if(nodes.length > 0) {
            this.nodes.addAll(Arrays.asList(nodes));
        }
        return this;
    }

    @Override
    public List<ExecutableNode> getNodes() {
        return nodes;
    }

    @Override
    public boolean execute(Context context) throws Exception {
        if(rule != null && !rule.match(context)) {
            return false;
        }
        return super.execute(context);
    }

    @Override
    protected boolean doExecute(Context context) throws Exception {
        return SERIAL_PROCEEDED.execute(getExecutor(), context, this);
    }

    @Override
    public Branch processOn(Rule rule) {
        this.rule = rule;
        return this;
    }

    @Override
    public Rule getRule() {
        return rule;
    }
}
