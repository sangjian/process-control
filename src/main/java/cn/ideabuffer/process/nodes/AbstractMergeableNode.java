package cn.ideabuffer.process.nodes;

import cn.ideabuffer.process.MergeableNode;
import cn.ideabuffer.process.rule.Rule;

/**
 * @author sangjian.sj
 * @date 2020/03/07
 */
public abstract class AbstractMergeableNode<T> extends AbstractNode implements MergeableNode<T> {

    private Rule rule;

    @Override
    public MergeableNode<T> processOn(Rule rule) {
        this.rule = rule;
        return this;
    }

    @Override
    public Rule getRule() {
        return rule;
    }
}
