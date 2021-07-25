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

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author sangjian.sj
 * @date 2020/03/07
 */
public abstract class AbstractMergeableNode extends AbstractNode implements Mergeable, Matchable, KeyManager {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 执行规则
     */
    private Rule rule;

    /**
     * 当前节点处理器执行超时时间
     */
    private long timeout;

    private KeyMapper keyMapper;
    private Set<Key<?>> readableKeys;
    private Set<Key<?>> writableKeys;

    public AbstractMergeableNode() {
    }

    public AbstractMergeableNode(Rule rule, long timeout) {
        this.rule = rule;
        this.timeout = timeout;
    }

    public AbstractMergeableNode(Rule rule, long timeout, Set<Key<?>> readableKeys, Set<Key<?>> writableKeys, KeyMapper keyMapper) {
        this.rule = rule;
        this.timeout = timeout;
        this.readableKeys = readableKeys == null ? new HashSet<>() : readableKeys;
        this.writableKeys = writableKeys == null ? new HashSet<>() : writableKeys;
        this.keyMapper = keyMapper;
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

    @Override
    public Set<Key<?>> getReadableKeys() {
        return this.readableKeys;
    }

    @Override
    public void setReadableKeys(Set<Key<?>> keys) {
        this.readableKeys = keys == null ? new HashSet<>() : keys;
    }

    @Override
    public Set<Key<?>> getWritableKeys() {
        return this.writableKeys;
    }

    @Override
    public void setWritableKeys(Set<Key<?>> keys) {
        this.writableKeys = keys == null ? new HashSet<>() : keys;
    }

    @Override
    public KeyMapper getKeyMapper() {
        return this.keyMapper;
    }

    @Override
    public void setKeyMapper(KeyMapper mapper) {
        this.keyMapper = mapper;
    }

}
