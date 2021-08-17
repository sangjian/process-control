package cn.ideabuffer.process.core.nodes.builder;

import cn.ideabuffer.process.core.Builder;
import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.context.KeyMapper;
import cn.ideabuffer.process.core.nodes.DefaultGenericMergeableNode;
import cn.ideabuffer.process.core.nodes.GenericMergeableNode;
import cn.ideabuffer.process.core.rules.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author sangjian.sj
 * @date 2020/04/24
 */
public class GenericMergeableNodeBuilder<T> implements Builder<GenericMergeableNode<T>> {

    private Rule rule;
    private long timeout;
    private TimeUnit timeUnit;
    private Processor<T> processor;
    private KeyMapper keyMapper;
    private Set<Key<?>> readableKeys;
    private Set<Key<?>> writableKeys;

    private GenericMergeableNodeBuilder() {
        this.readableKeys = new HashSet<>();
        this.writableKeys = new HashSet<>();
    }

    public static <T> GenericMergeableNodeBuilder<T> newBuilder() {
        return new GenericMergeableNodeBuilder<>();
    }

    public GenericMergeableNodeBuilder<T> processOn(Rule rule) {
        this.rule = rule;
        return this;
    }

    public GenericMergeableNodeBuilder<T> timeout(long timeout, @NotNull TimeUnit unit) {
        this.timeout = timeout;
        this.timeUnit = unit;
        return this;
    }

    public GenericMergeableNodeBuilder<T> by(Processor<T> processor) {
        this.processor = processor;
        return this;
    }

    public GenericMergeableNodeBuilder<T> keyMapper(KeyMapper keyMapper) {
        this.keyMapper = keyMapper;
        return this;
    }

    public GenericMergeableNodeBuilder<T> readableKeys(@NotNull Key<?>... keys) {
        this.readableKeys.addAll(Arrays.stream(keys).collect(Collectors.toSet()));
        return this;
    }

    public GenericMergeableNodeBuilder<T> readableKeys(@NotNull Set<Key<?>> keys) {
        this.readableKeys = keys;
        return this;
    }

    public GenericMergeableNodeBuilder<T> writableKeys(@NotNull Key<?>... keys) {
        this.writableKeys.addAll(Arrays.stream(keys).collect(Collectors.toSet()));
        return this;
    }

    public GenericMergeableNodeBuilder<T> writableKeys(@NotNull Set<Key<?>> keys) {
        this.writableKeys = keys;
        return this;
    }

    @Override
    public GenericMergeableNode<T> build() {
        long millis = timeUnit == null ? 0L : timeUnit.toMillis(timeout);
        return new DefaultGenericMergeableNode<>(rule, millis, processor, readableKeys, writableKeys, keyMapper);
    }
}
