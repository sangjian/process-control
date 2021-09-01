package cn.ideabuffer.process.core.aggregators;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.DistributeMergeableNode;
import cn.ideabuffer.process.core.utils.AggregateUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * 串分布式聚合器
 *
 * @param <R> 聚合结果类型
 * @author sangjian.sj
 * @date 2021/09/01
 * @see DistributeAggregator
 */
public class SerialDistributeAggregator<R> extends AbstractAggregator implements DistributeAggregator<R> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 返回结果类型，该类型必须有无参构造器
     */
    private Class<R> resultClass;

    public SerialDistributeAggregator(@NotNull Class<R> resultClass) {
        this.resultClass = resultClass;
    }

    @NotNull
    @Override
    public R aggregate(@NotNull Context context, List<DistributeMergeableNode<?, R>> nodes) throws Exception {
        R result;
        // 创建结果对象
        try {
            result = resultClass.newInstance();
        } catch (Exception e) {
            logger.error("aggregate error, resultClass:{}, nodes:{}", resultClass, nodes, e);
            throw e;
        }
        if (nodes == null || nodes.isEmpty()) {
            return result;
        }
        List<MergerNode<?, R>> mergerNodes = new LinkedList<>();
        for (DistributeMergeableNode<?, R> node : nodes) {
            Object value = AggregateUtils.process(context, node);
            MergerNode<?, R> m = new MergerNode(node, value, result);
            mergerNodes.add(m);
        }

        for (MergerNode<?, R> mergerNode : mergerNodes) {
            mergerNode.merge();
        }
        return result;
    }

    private static class MergerNode<V, R> {
        private DistributeMergeableNode<V, R> node;
        private V value;
        private R result;

        MergerNode(DistributeMergeableNode<V, R> node, V value, R result) {
            this.node = node;
            this.value = value;
            this.result = result;
        }

        public void merge() {
            if (node.getProcessor() != null) {
                node.getProcessor().merge(value, result);
            }
        }
    }
}
