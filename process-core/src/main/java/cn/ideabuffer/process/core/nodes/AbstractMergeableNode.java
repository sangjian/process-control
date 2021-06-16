package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.Matchable;
import cn.ideabuffer.process.core.Mergeable;
import cn.ideabuffer.process.core.rule.Rule;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author sangjian.sj
 * @date 2020/03/07
 */
public abstract class AbstractMergeableNode extends AbstractNode implements Mergeable, Matchable {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private Rule rule;
    private long timeout;

    public AbstractMergeableNode() {
    }

    public AbstractMergeableNode(Rule rule, long timeout) {
        this.rule = rule;
        this.timeout = timeout;
    }

    @Override
    public void processOn(Rule rule) {
        this.rule = rule;
    }

    @Override
    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    @Override
    public void timeout(long timeout, @NotNull TimeUnit unit) {
        this.timeout = unit.toMillis(timeout);
    }

    @Override
    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

}
