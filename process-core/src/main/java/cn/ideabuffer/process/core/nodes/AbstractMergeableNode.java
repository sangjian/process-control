package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.KeyManager;
import cn.ideabuffer.process.core.Matchable;
import cn.ideabuffer.process.core.Mergeable;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.context.KeyMapper;
import cn.ideabuffer.process.core.rules.Rule;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author sangjian.sj
 * @date 2020/03/07
 */
public abstract class AbstractMergeableNode extends AbstractKeyManagerNode implements Mergeable, Matchable, KeyManager {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 执行规则
     */
    private Rule rule;

    /**
     * 当前节点处理器执行超时时间
     */
    private long timeout;

    public AbstractMergeableNode() {
    }

    public AbstractMergeableNode(Rule rule, long timeout) {
        this.rule = rule;
        this.timeout = timeout;
    }

    public AbstractMergeableNode(Rule rule, long timeout, Set<Key<?>> readableKeys, Set<Key<?>> writableKeys, KeyMapper keyMapper) {
        super(keyMapper, readableKeys, writableKeys);
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
