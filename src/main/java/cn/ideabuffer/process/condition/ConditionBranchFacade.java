package cn.ideabuffer.process.condition;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.ExecutableNode;
import cn.ideabuffer.process.branch.Branch;
import cn.ideabuffer.process.rule.Rule;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class ConditionBranchFacade implements Branch {

    private Rule rule;

    private Branch branch;

    public ConditionBranchFacade(Rule rule, Branch branch) {
        this.rule = rule;
        this.branch = branch;
    }

    @Override
    public Branch addNodes(ExecutableNode... nodes) {return branch.addNodes(nodes);}

    @Override
    public List<ExecutableNode> getNodes() {return branch.getNodes();}

    @Override
    public Branch processOn(Rule rule) {
        this.rule = rule;
        return this;
    }

    @Override
    public Rule getRule() {return rule;}

    @Override
    public boolean execute(Context context) throws Exception {return branch.execute(context);}

    @Override
    public ExecutorService getExecutor() {return branch.getExecutor();}

    @Override
    public ExecutableNode parallel() {return branch.parallel();}

    @Override
    public ExecutableNode parallel(ExecutorService executor) {return branch.parallel(executor);}

    @Override
    public String getId() {return branch.getId();}

    @Override
    public boolean enabled(Context context) {return branch.enabled(context);}
}
