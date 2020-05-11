package cn.ideabuffer.process.core.test;

import cn.ideabuffer.process.core.*;
import cn.ideabuffer.process.core.aggregator.Aggregators;
import cn.ideabuffer.process.core.aggregator.DistributeAggregator;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.nodes.DistributeMergeableNode;
import cn.ideabuffer.process.core.nodes.MergeableNode;
import cn.ideabuffer.process.core.nodes.aggregate.DistributeAggregatableNode;
import cn.ideabuffer.process.core.nodes.aggregate.GenericAggregatableNode;
import cn.ideabuffer.process.core.nodes.aggregate.UnitAggregatableNode;
import cn.ideabuffer.process.core.nodes.builder.*;
import cn.ideabuffer.process.core.nodes.merger.*;
import cn.ideabuffer.process.core.processors.impl.UnitAggregateProcessorImpl;
import cn.ideabuffer.process.core.test.merger.TestStringListMerger;
import cn.ideabuffer.process.core.test.nodes.aggregate.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author sangjian.sj
 * @date 2020/03/09
 */
public class AggregateTest {

    private static final Logger logger = LoggerFactory.getLogger(AggregateTest.class);

    @Test
    public void testUnitAggregateList() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();

        Executor executor = Executors.newFixedThreadPool(3);

        MergeableNode<List<String>> node1 = MergeNodeBuilder.<List<String>>newBuilder().by(new TestListMergeNodeProcessor1()).build();
        MergeableNode<List<String>> node2 = MergeNodeBuilder.<List<String>>newBuilder().by(new TestListMergeNodeProcessor2()).build();

        List<MergeableNode<List<String>>> nodes = new ArrayList<>();
        nodes.add(node1);
        nodes.add(node2);

        // 创建单元化聚合节点
        UnitAggregatableNode<List<String>> node = UnitAggregatableNodeBuilder.<List<String>>newBuilder().aggregator(
            Aggregators.newParallelUnitAggregator(executor, new ArrayListMerger<>()))
            .aggregate(nodes)
            .addListeners(new TestUnitAggregatableNodeListener1(), new TestUnitAggregatableNodeListener2())
            .by(new UnitAggregateProcessorImpl<>())
            .build();

        // 链式结果处理
        node.thenApply(((ctx, result) -> {
            logger.info("result:{}", result);
            return result.size();
        })).thenAccept((ctx, result) -> logger.info("result:{}", result));
        definition.addAggregateNode(node);

        ProcessInstance<String> instance = definition.newInstance();
        Context context = Contexts.newContext();

        instance.execute(context);
    }

    @Test
    public void testGenericAggregateList() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();

        Executor executor = Executors.newFixedThreadPool(3);

        MergeableNode<String> node1 = MergeNodeBuilder.<String>newBuilder().by(new TestStringMergeNodeProcessor1()).build();
        MergeableNode<String> node2 = MergeNodeBuilder.<String>newBuilder().by(new TestStringMergeNodeProcessor2()).build();

        List<MergeableNode<String>> nodes = new ArrayList<>();
        nodes.add(node1);
        nodes.add(node2);

        // 创建通用聚合节点
        GenericAggregatableNode<String, List<String>> node
            = GenericAggregatableNodeBuilder.<String, List<String>>newBuilder().aggregator(
            Aggregators.newParallelGenericAggregator(executor, new TestStringListMerger())).aggregate(nodes)
            .build();
        // 链式结果处理
        node.thenApply(((ctx, result) -> {
            logger.info("result:{}", result);
            return result.size();
        })).thenAccept((ctx, result) -> logger.info("result:{}", result));
        definition.addAggregateNode(node);

        ProcessInstance<String> instance = definition.newInstance();
        Context context = Contexts.newContext();

        instance.execute(context);
    }

    @Test
    public void testIntSum() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        MergeableNode<Integer> node1 = MergeNodeBuilder.<Integer>newBuilder().by(new IntMergeNodeProcessor1()).build();
        MergeableNode<Integer> node2 = MergeNodeBuilder.<Integer>newBuilder().by(new IntMergeNodeProcessor2()).build();

        List<MergeableNode<Integer>> nodes = new ArrayList<>();
        nodes.add(node1);
        nodes.add(node2);

        UnitAggregatableNode<Integer> node = UnitAggregatableNodeBuilder.<Integer>newBuilder().aggregator(
            Aggregators.newSerialUnitAggregator(new IntSumMerger())).aggregate(nodes).build();

        node.thenApply(((ctx, result) -> {
            System.out.println(result);
            return result;
        }));
        definition.addAggregateNode(node);

        ProcessInstance<String> instance = definition.newInstance();
        Context context = Contexts.newContext();

        instance.execute(context);
        //Thread.sleep(10000);
    }

    @Test
    public void testIntAvg() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        MergeableNode<Integer> node1 = MergeNodeBuilder.<Integer>newBuilder().by(new IntMergeNodeProcessor1()).build();
        MergeableNode<Integer> node2 = MergeNodeBuilder.<Integer>newBuilder().by(new IntMergeNodeProcessor2()).build();

        List<MergeableNode<Integer>> nodes = new ArrayList<>();
        nodes.add(node1);
        nodes.add(node2);

        UnitAggregatableNode<Integer> node = UnitAggregatableNodeBuilder.<Integer>newBuilder().aggregator(
            Aggregators.newSerialUnitAggregator(new IntAvgMerger())).aggregate(nodes).build();

        node.thenApply(((ctx, result) -> {
            System.out.println(result);
            return result;
        }));
        definition.addAggregateNode(node);

        ProcessInstance<String> instance = definition.newInstance();
        Context context = Contexts.newContext();

        instance.execute(context);
        //Thread.sleep(10000);
    }

    @Test
    public void testDoubleSum() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        MergeableNode<Double> node1 = MergeNodeBuilder.<Double>newBuilder().by(new DoubleMergeNodeProcessor1()).build();
        MergeableNode<Double> node2 = MergeNodeBuilder.<Double>newBuilder().by(new DoubleMergeNodeProcessor2()).build();

        List<MergeableNode<Double>> nodes = new ArrayList<>();
        nodes.add(node1);
        nodes.add(node2);

        UnitAggregatableNode<Double> node = UnitAggregatableNodeBuilder.<Double>newBuilder().aggregator(
            Aggregators.newSerialUnitAggregator(new DoubleSumMerger())).aggregate(nodes).build();
        node.thenApply(((ctx, result) -> {
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
        MergeableNode<Double> node1 = MergeNodeBuilder.<Double>newBuilder().by(new DoubleMergeNodeProcessor1()).build();
        MergeableNode<Double> node2 = MergeNodeBuilder.<Double>newBuilder().by(new DoubleMergeNodeProcessor2()).build();

        List<MergeableNode<Double>> nodes = new ArrayList<>();
        nodes.add(node1);
        nodes.add(node2);

        UnitAggregatableNode<Double> node = UnitAggregatableNodeBuilder.<Double>newBuilder().aggregator(
            Aggregators.newSerialUnitAggregator(new DoubleAvgMerger())).aggregate(nodes).build();
        node.thenApply(((ctx, result) -> {
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
        MergeableNode<int[]> node1 = MergeNodeBuilder.<int[]>newBuilder().by(new IntArrayMergeNodeProcessor1()).build();
        MergeableNode<int[]> node2 = MergeNodeBuilder.<int[]>newBuilder().by(new IntArrayMergeNodeProcessor2()).build();
        List<MergeableNode<int[]>> nodes = new ArrayList<>();
        nodes.add(node1);
        nodes.add(node2);
        UnitAggregatableNode<int[]> node = UnitAggregatableNodeBuilder.<int[]>newBuilder().aggregator(
            Aggregators.newSerialUnitAggregator(new IntArrayMerger())).aggregate(nodes).build();
        node.thenApply(((ctx, result) -> {
            Arrays.stream(result).forEach(System.out::println);
            return result;
        }));
        definition.addAggregateNode(node);
        ProcessInstance<String> instance = new DefaultProcessInstance<>(definition);
        Context context = Contexts.newContext();

        instance.execute(context);
        //Thread.sleep(10000);
    }

    @Test
    public void testDistributeAggregate() throws Exception {
        Executor executor = Executors.newFixedThreadPool(3);
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        // 指定线程池和返回结果类型，创建分布式聚合器
        DistributeAggregator<Person> aggregator = Aggregators.newParallelDistributeAggregator(executor, Person.class);

        DistributeMergeableNode<Integer, Person> node1 = DistributeMergeNodeBuilder.<Integer, Person>newBuilder().by(new TestDistributeMergeNodeProcessor1()).build();
        DistributeMergeableNode<String, Person> node2 = DistributeMergeNodeBuilder.<String, Person>newBuilder().by(new TestDistributeMergeNodeProcessor2()).build();

        List<DistributeMergeableNode<?, Person>> nodes = new ArrayList<>();
        nodes.add(node1);
        nodes.add(node2);
        // 创建分布式聚合节点
        DistributeAggregatableNode<Person> node = DistributeAggregatableNodeBuilder.<Person>newBuilder()
            .aggregator(Aggregators.newParallelDistributeAggregator(executor, Person.class))
            // 注册分布式可合并节点，并设置结果处理器
            .aggregate(nodes).build();
        node.thenApply((ctx, result) -> {
            logger.info("result: {}", result);
            return result;
        });

        // 注册分布式聚合节点
        definition.addDistributeAggregateNode(node);
        ProcessInstance<String> instance = definition.newInstance();
        instance.execute(Contexts.newContext());
        Thread.sleep(10000);
    }

}
