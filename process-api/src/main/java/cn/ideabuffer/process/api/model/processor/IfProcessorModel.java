package cn.ideabuffer.process.api.model.processor;

import cn.ideabuffer.process.api.model.ModelBuilderFactory;
import cn.ideabuffer.process.api.model.RuleModel;
import cn.ideabuffer.process.api.model.builder.BranchNodeModelBuilder;
import cn.ideabuffer.process.api.model.builder.RuleModelBuilder;
import cn.ideabuffer.process.api.model.node.BranchNodeModel;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.processors.IfProcessor;
import cn.ideabuffer.process.core.rules.Rule;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/06/22
 */
public class IfProcessorModel<R extends IfProcessor> extends ProcessorModel<R> {
    private static final long serialVersionUID = 1656596845938118061L;

    private RuleModel ruleModel;
    private BranchNodeModel trueBranchModel;
    private BranchNodeModel falseBranchModel;

    public IfProcessorModel(@NotNull R processor) {
        super(processor);
    }

    public RuleModel getRuleModel() {
        return ruleModel;
    }

    public void setRuleModel(RuleModel ruleModel) {
        this.ruleModel = ruleModel;
    }

    public BranchNodeModel getTrueBranchModel() {
        return trueBranchModel;
    }

    public void setTrueBranchModel(BranchNodeModel trueBranchModel) {
        this.trueBranchModel = trueBranchModel;
    }

    public BranchNodeModel getFalseBranchModel() {
        return falseBranchModel;
    }

    public void setFalseBranchModel(BranchNodeModel falseBranchModel) {
        this.falseBranchModel = falseBranchModel;
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
        BranchNode trueBranch = resource.getTrueBranch();
        BranchNodeModelBuilder<BranchNode> trueBranchBuilder = factory.getModelBuilder(trueBranch);
        if (trueBranchBuilder != null) {
            this.trueBranchModel = trueBranchBuilder.build(trueBranch);
        }
        BranchNode falseBranch = resource.getFalseBranch();
        BranchNodeModelBuilder<BranchNode> falseBranchBuilder = factory.getModelBuilder(falseBranch);
        if (falseBranchBuilder != null) {
            this.falseBranchModel = falseBranchBuilder.build(falseBranch);
        }
    }
}
