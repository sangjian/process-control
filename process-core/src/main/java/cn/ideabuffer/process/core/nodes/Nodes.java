package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.context.KeyMapper;
import cn.ideabuffer.process.core.nodes.aggregate.*;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.nodes.branch.DefaultBranchNode;
import cn.ideabuffer.process.core.processors.impl.GenericAggregateProcessorImpl;
import cn.ideabuffer.process.core.rules.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class Nodes {

    private Nodes() {
        throw new IllegalStateException("Utility class");
    }

    public static <R> ProcessNode<R> newProcessNode() {
        return new ProcessNode<>();
    }

    public static <R> ProcessNode<R> newProcessNode(Processor<R> processor) {
        return newProcessNode(processor, null, null);
    }

    public static <R> ProcessNode<R> newProcessNode(Processor<R> processor, Set<Key<?>> readableKeys,
        Set<Key<?>> writableKeys) {
        return newProcessNode(processor, null, readableKeys, writableKeys);
    }

    public static <R> ProcessNode<R> newProcessNode(Processor<R> processor, KeyMapper mapper, Set<Key<?>> readableKeys,
        Set<Key<?>> writableKeys) {
        return new ProcessNode<>(processor, mapper, readableKeys, writableKeys);
    }

    public static NodeGroup newGroup() {
        return new NodeGroup();
    }

    public static BranchNode newBranch() {
        return new DefaultBranchNode();
    }

    public static BranchNode newBranch(@NotNull List<ExecutableNode<?, ?>> nodes) {
        return newBranch(null, nodes);
    }

    public static BranchNode newBranch(Rule rule, @NotNull List<ExecutableNode<?, ?>> nodes) {
        return new DefaultBranchNode(rule, nodes);
    }

    public static BranchNode newBranch(@NotNull ExecutableNode<?, ?>... nodes) {
        return newBranch(null, Arrays.asList(nodes));
    }

    public static BranchNode newBranch(Rule rule, @NotNull ExecutableNode<?, ?>... nodes) {
        return newBranch(rule, Arrays.asList(nodes));
    }

    public static ParallelBranchNode newParallelBranchNode() {
        return newParallelBranchNode(null);
    }

    public static ParallelBranchNode newParallelBranchNode(Rule rule) {
        return newParallelBranchNode(rule, null);
    }

    public static ParallelBranchNode newParallelBranchNode(Rule rule, List<BranchNode> branches) {
        return newParallelBranchNode(rule, null, branches);
    }

    public static ParallelBranchNode newParallelBranchNode(Rule rule, Executor executor, List<BranchNode> branches) {
        return new DefaultParallelBranchNode(rule, executor, branches);
    }

    public static <R> UnitAggregatableNode<R> newUnitAggregatableNode() {
        return new DefaultUnitAggregatableNode<>();
    }

    public static <P, R> GenericAggregatableNode<P, R> newGenericAggregatableNode() {
        return new DefaultGenericAggregatableNode<>();
    }

    public static <P, R> GenericAggregatableNode<P, R> newGenericAggregatableNode(
        GenericAggregateProcessorImpl<P, R> processor) {
        return new DefaultGenericAggregatableNode<>(processor);
    }

    public static <R> DistributeAggregatableNode<R> newDistributeAggregatableNode() {
        return new DefaultDistributeAggregatableNode<>();
    }
}
