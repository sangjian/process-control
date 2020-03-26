package cn.ideabuffer.process.test;

import cn.ideabuffer.process.*;
import cn.ideabuffer.process.context.Context;
import cn.ideabuffer.process.context.Contexts;
import cn.ideabuffer.process.nodes.AggregatableNode;
import cn.ideabuffer.process.nodes.Nodes;
import cn.ideabuffer.process.nodes.aggregate.Aggregators;
import cn.ideabuffer.process.test.nodes.aggregate.*;
import cn.ideabuffer.process.nodes.merger.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author sangjian.sj
 * @date 2020/03/09
 */
public class AggregateTest {

    private static final Logger logger = LoggerFactory.getLogger(AggregateTest.class);

    @Test
    public void testAggregateList() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();

        AggregatableNode<List<String>> node = Nodes.newAggregatableNode();
        Executor executor = Executors.newFixedThreadPool(3);

        node.aggregator(Aggregators.newParallelAggregator(executor, new ArrayListMerger<>())).merge(
            new TestMergeableNode1(), new TestMergeableNode2())
            .thenApply(((ctx, result) -> {
                logger.info("result:{}", result);
                return result.size();
            })).thenApplyAsync((ctx, result) -> {
            logger.info("result:{}", result);
            return null;
        }).thenAccept((ctx, result) -> logger.info("result:{}", result));
        definition.addAggregateNode(node);

        ProcessInstance<String> instance = new DefaultProcessInstance<>(definition);
        Context context = Contexts.newContext();

        instance.execute(context);
        //Thread.sleep(10000);
    }

    @Test
    public void testIntSum() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        AggregatableNode<Integer> node = Nodes.newAggregatableNode();
        node.aggregator(Aggregators.newSerialAggregator(new IntSumMerger())).merge(new IntMergeableNode1(),
            new IntMergeableNode2())
            .thenApply(((ctx, result) -> {
                System.out.println(result);
                return result;
            }));
        definition.addAggregateNode(node);

        ProcessInstance<String> instance = new DefaultProcessInstance<>(definition);
        Context context = Contexts.newContext();

        instance.execute(context);
        //Thread.sleep(10000);
    }

    @Test
    public void testIntAvg() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        AggregatableNode<Integer> node = Nodes.newAggregatableNode();
        node.aggregator(Aggregators.newSerialAggregator(new IntAvgMerger())).merge(new IntMergeableNode1(),
            new IntMergeableNode2())
            .thenApply(((ctx, result) -> {
                System.out.println(result);
                return result;
            }));
        definition.addAggregateNode(node);

        ProcessInstance<String> instance = new DefaultProcessInstance<>(definition);
        Context context = Contexts.newContext();

        instance.execute(context);
        //Thread.sleep(10000);
    }

    @Test
    public void testDoubleSum() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        AggregatableNode<Double> node = Nodes.newAggregatableNode();
        node.aggregator(Aggregators.newSerialAggregator(new DoubleSumMerger())).merge(new DoubleMergeableNode1(),
            new DoubleMergeableNode2())
            .thenApply(((ctx, result) -> {
                System.out.println(result);
                return result;
            }));
        definition.addAggregateNode(node);
        ProcessInstance<String> instance = new DefaultProcessInstance<>(definition);
        Context context = Contexts.newContext();

        instance.execute(context);
        //Thread.sleep(10000);
    }

    @Test
    public void testDoubleAvg() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        AggregatableNode<Double> node = Nodes.newAggregatableNode();
        node.aggregator(Aggregators.newSerialAggregator(new DoubleAvgMerger())).merge(new DoubleMergeableNode1(),
            new DoubleMergeableNode2())
            .thenApply(((ctx, result) -> {
                System.out.println(result);
                return result;
            }));
        definition.addAggregateNode(node);
        ProcessInstance<String> instance = new DefaultProcessInstance<>(definition);
        Context context = Contexts.newContext();

        instance.execute(context);
        //Thread.sleep(10000);
    }

    @Test
    public void testIntArray() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        AggregatableNode<int[]> node = Nodes.newAggregatableNode();
        node.aggregator(Aggregators.newSerialAggregator(new IntArrayMerger())).merge(new IntArrayMergeableNode1(),
            new IntArrayMergeableNode2())
            .thenApply(((ctx, result) -> {
                Arrays.stream(result).forEach(System.out::println);
                return result;
            }));
        definition.addAggregateNode(node);
        ProcessInstance<String> instance = new DefaultProcessInstance<>(definition);
        Context context = Contexts.newContext();

        instance.execute(context);
        //Thread.sleep(10000);
    }

}
