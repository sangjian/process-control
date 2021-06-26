package cn.ideabuffer.process.api.model.processor;

import cn.ideabuffer.process.api.model.ModelBuilderFactory;
import cn.ideabuffer.process.api.model.RuleModel;
import cn.ideabuffer.process.api.model.builder.BranchNodeModelBuilder;
import cn.ideabuffer.process.api.model.builder.RuleModelBuilder;
import cn.ideabuffer.process.api.model.node.BranchNodeModel;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.processors.WhileProcessor;
import cn.ideabuffer.process.core.rules.Rule;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/06/22
 */
public class WhileProcessorModel<R extends WhileProcessor> extends ProcessorModel<R> {

    private static final long serialVersionUID = 7517527441917002800L;
    private RuleModel ruleModel;
    private BranchNodeModel branchModel;

    public WhileProcessorModel(@NotNull R processor) {
        super(processor);
    }

    public RuleModel getRuleModel() {
        return ruleModel;
    }

    public void setRuleModel(RuleModel ruleModel) {
        this.ruleModel = ruleModel;
    }

    public BranchNodeModel getBranchModel() {
        return branchModel;
    }

    public void setBranchModel(BranchNodeModel branchModel) {
        this.branchModel = branchModel;
    }

    @Override
    public void init() {
        super.init();
        ModelBuilderFactory factory = ModelBuilderFactory.getInstance();
        Rule rule = resource.getRule();
        RuleModelBuilder<Rule> ruleModelBuilder = factory.getModelBuilder(rule);
        if (ruleModelBuilder != null) {
            this.ruleModel = ruleModelBuilder.build(rule);
        }
        BranchNode branch = resource.getBranch();
        BranchNodeModelBuilder<BranchNode> trueBranchBuilder = factory.getModelBuilder(branch);
        if (trueBranchBuilder != null) {
            this.branchModel = trueBranchBuilder.build(branch);
        }
    }
}
