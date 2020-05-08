package cn.ideabuffer.process.core.test;

import cn.ideabuffer.process.core.*;
import cn.ideabuffer.process.core.aggregator.Aggregators;
import cn.ideabuffer.process.core.aggregator.DistributeAggregator;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.nodes.aggregate.DistributeAggregatableNode;
import cn.ideabuffer.process.core.nodes.aggregate.GenericAggregatableNode;
import cn.ideabuffer.process.core.nodes.aggregate.UnitAggregatableNode;
import cn.ideabuffer.process.core.nodes.builder.DistributeAggregatableNodeBuilder;
import cn.ideabuffer.process.core.nodes.builder.GenericAggregatableNodeBuilder;
import cn.ideabuffer.process.core.nodes.builder.UnitAggregatableNodeBuilder;
import cn.ideabuffer.process.core.nodes.merger.*;
import cn.ideabuffer.process.core.test.merger.TestStringListMerger;
import cn.ideabuffer.process.core.test.nodes.aggregate.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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


        // 创建单元化聚合节点
        UnitAggregatableNode<List<String>> node = UnitAggregatableNodeBuilder.<List<String>>newBuilder().aggregator(
            Aggregators.newParallelUnitAggregator(executor, new ArrayListMerger<>()))
            .aggregate(new TestListMergeableNode1(), new TestListMergeableNode2())
            .addListeners(new TestUnitAggregatableNodeListener1(), new TestUnitAggregatableNodeListener2())
            //.by(new UnitAggregateProcessor<>())
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
        // 创建通用聚合节点
        GenericAggregatableNode<String, List<String>> node
            = GenericAggregatableNodeBuilder.<String, List<String>>newBuilder().aggregator(
            Aggregators.newParallelGenericAggregator(executor, new TestStringListMerger())).aggregate(
            new TestStringMergeableNode1(), new TestStringMergeableNode2())
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
        UnitAggregatableNode<Integer> node = UnitAggregatableNodeBuilder.<Integer>newBuilder().aggregator(
            Aggregators.newSerialUnitAggregator(new IntSumMerger())).aggregate(new IntMergeableNode1(),
            new IntMergeableNode2()).build();

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
    public void testIntAvg() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        UnitAggregatableNode<Integer> node = UnitAggregatableNodeBuilder.<Integer>newBuilder().aggregator(
            Aggregators.newSerialUnitAggregator(new IntAvgMerger())).aggregate(new IntMergeableNode1(),
            new IntMergeableNode2()).build();

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
    public void testDoubleSum() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        UnitAggregatableNode<Double> node = UnitAggregatableNodeBuilder.<Double>newBuilder().aggregator(
            Aggregators.newSerialUnitAggregator(new DoubleSumMerger())).aggregate(
            new DoubleMergeableNode1(),
            new DoubleMergeableNode2()).build();
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
        UnitAggregatableNode<Double> node = UnitAggregatableNodeBuilder.<Double>newBuilder().aggregator(
            Aggregators.newSerialUnitAggregator(new DoubleAvgMerger())).aggregate(
            new DoubleMergeableNode1(),
            new DoubleMergeableNode2()).build();
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
        UnitAggregatableNode<int[]> node = UnitAggregatableNodeBuilder.<int[]>newBuilder().aggregator(
            Aggregators.newSerialUnitAggregator(new IntArrayMerger())).aggregate(
            new IntArrayMergeableNode1(),
            new IntArrayMergeableNode2()).build();
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
        // 创建分布式聚合节点
        DistributeAggregatableNode<Person> node = DistributeAggregatableNodeBuilder.<Person>newBuilder()
            // 注册分布式可合并节点，并设置结果处理器
            .aggregate(new TestDistributeMergeNode1(), new TestDistributeMergeNode2()).build();
        node.thenApply((ctx, result) -> {
            logger.info("result: {}", result);
            return result;
        });
        // 注册分布式聚合节点
        definition.addAggregateNode(node);
        ProcessInstance<String> instance = definition.newInstance();
        instance.execute(null);
    }

}
