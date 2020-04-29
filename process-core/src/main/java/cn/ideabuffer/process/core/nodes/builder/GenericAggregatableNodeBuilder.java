package cn.ideabuffer.process.core.nodes.builder;

import cn.ideabuffer.process.core.ProcessListener;
import cn.ideabuffer.process.core.aggregator.GenericAggregator;
import cn.ideabuffer.process.core.nodes.MergeableNode;
import cn.ideabuffer.process.core.nodes.Nodes;
import cn.ideabuffer.process.core.nodes.aggregate.GenericAggregatableNode;
import cn.ideabuffer.process.core.rule.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/04/24
 */
public class GenericAggregatableNodeBuilder<P, R>
    extends AbstractExecutableNodeBuilder<GenericAggregatableNode<P, R>> {

    private List<MergeableNode<P>> mergeableNodes;

    private GenericAggregator<P, R> aggregator;

    private GenericAggregatableNodeBuilder(GenericAggregatableNode<P, R> node) {
        super(node);
        mergeableNodes = new ArrayList<>();
    }

    public static <P, R> GenericAggregatableNodeBuilder<P, R> newBuilder() {
        GenericAggregatableNode<P, R> node = Nodes.newGenericAggregatableNode();
        return new GenericAggregatableNodeBuilder<>(node);
    }

    @Override
    public GenericAggregatableNodeBuilder<P, R> parallel() {
        super.parallel();
        return this;
    }

    @Override
    public GenericAggregatableNodeBuilder<P, R> parallel(Executor executor) {
        super.parallel(executor);
        return this;
    }

    @Override
    public GenericAggregatableNodeBuilder<P, R> processOn(Rule rule) {
        super.processOn(rule);
        return this;
    }

    @Override
    public GenericAggregatableNodeBuilder<P, R> addListeners(ProcessListener... listeners) {
        super.addListeners(listeners);
        return this;
    }

    public GenericAggregatableNodeBuilder<P, R> aggregate(@NotNull MergeableNode<P>... nodes) {
        this.mergeableNodes.addAll(Arrays.asList(nodes));
        return this;
    }

    public GenericAggregatableNodeBuilder<P, R> aggregator(@NotNull GenericAggregator<P, R> aggregator) {
        this.aggregator = aggregator;
        return this;
    }

    @Override
    public GenericAggregatableNode<P, R> build() {
        GenericAggregatableNode<P, R> node = super.build();
        node.aggregate(mergeableNodes);
        node.aggregator(aggregator);
        return node;
    }
}
