package cn.ideabuffer.process.nodes;

import cn.ideabuffer.process.AggregatableNode;
import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.MergeableNode;
import cn.ideabuffer.process.Merger;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/03/07
 */
public abstract class AbstractAggregatableNode<T> extends AbstractNode implements AggregatableNode<T> {

    private boolean parallel = false;

    protected Executor executor;

    private List<MergeableNode<T>> mergeableNodes;

    private Merger<T> merger;

    public AbstractAggregatableNode() {
    }

    public AbstractAggregatableNode(List<MergeableNode<T>> mergeableNodes) {
        this.mergeableNodes = mergeableNodes;
    }

    public void setParallel(boolean parallel) {
        this.parallel = parallel;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    public void setMergeableNodes(List<MergeableNode<T>> mergeableNodes) {
        this.mergeableNodes = mergeableNodes;
    }

    @Override
    public AggregatableNode<T> merge(MergeableNode<T>... nodes) {
        if(nodes == null || nodes.length == 0) {
            return this;
        }
        this.mergeableNodes.addAll(Arrays.asList(nodes));
        return this;
    }

    @Override
    public AggregatableNode<T> by(Merger<T> merger) {
        this.merger = merger;
        return this;
    }

    @Override
    public AggregatableNode<T> parallel() {
        this.parallel = true;
        return this;
    }

    @Override
    public AggregatableNode<T> parallel(Executor executor) {
        this.parallel = true;
        this.executor = executor;
        return this;
    }

    @Override
    public T aggregate() {

        return null;
    }
}
