package cn.ideabuffer.process.test;

import cn.ideabuffer.process.DefaultProcessDefinition;
import cn.ideabuffer.process.DefaultProcessInstance;
import cn.ideabuffer.process.ProcessDefinition;
import cn.ideabuffer.process.ProcessInstance;
import cn.ideabuffer.process.context.Context;
import cn.ideabuffer.process.context.Contexts;
import cn.ideabuffer.process.nodes.DistributeAggregatableNode;
import cn.ideabuffer.process.nodes.Nodes;
import cn.ideabuffer.process.nodes.UnitAggregatableNode;
import cn.ideabuffer.process.nodes.aggregate.Aggregators;
import cn.ideabuffer.process.nodes.aggregate.DefaultDistributeAggregatableNode;
import cn.ideabuffer.process.nodes.aggregate.DistributeAggregator;
import cn.ideabuffer.process.nodes.merger.*;
import cn.ideabuffer.process.test.nodes.aggregate.*;
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

        UnitAggregatableNode<List<String>> node = Nodes.newUnitAggregatableNode();
        Executor executor = Executors.newFixedThreadPool(3);

        node.aggregator(Aggregators.newParallelUnitAggregator(executor, new ArrayListMerger<>())).aggregate(
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
        UnitAggregatableNode<Integer> node = Nodes.newUnitAggregatableNode();
        node.aggregator(Aggregators.newSerialUnitAggregator(new IntSumMerger())).aggregate(new IntMergeableNode1(),
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
        UnitAggregatableNode<Integer> node = Nodes.newUnitAggregatableNode();
        node.aggregator(Aggregators.newSerialUnitAggregator(new IntAvgMerger())).aggregate(new IntMergeableNode1(),
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
        UnitAggregatableNode<Double> node = Nodes.newUnitAggregatableNode();
        node.aggregator(Aggregators.newSerialUnitAggregator(new DoubleSumMerger())).aggregate(
            new DoubleMergeableNode1(),
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
        UnitAggregatableNode<Double> node = Nodes.newUnitAggregatableNode();
        node.aggregator(Aggregators.newSerialUnitAggregator(new DoubleAvgMerger())).aggregate(
            new DoubleMergeableNode1(),
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
        UnitAggregatableNode<int[]> node = Nodes.newUnitAggregatableNode();
        node.aggregator(Aggregators.newSerialUnitAggregator(new IntArrayMerger())).aggregate(
            new IntArrayMergeableNode1(),
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

    @Test
    public void testDistributeAggregate() throws Exception {
        Executor executor = Executors.newFixedThreadPool(3);
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        DistributeAggregator<Person> aggregator = Aggregators.newParallelDistributeAggregator(executor, Person.class);
        DistributeAggregatableNode<Person> node = new DefaultDistributeAggregatableNode<>(aggregator);
        node.aggregate(new TestDistributeMergeNode1(), new TestDistributeMergeNode2())
            .thenApply((ctx, result) -> {
                logger.info("age is : {}", result.getAge());
                return result.getName();
            })
            .thenApply((ctx, result) -> {
                logger.info("name is : {}", result);
                return result;
            });
        definition.addAggregateNode(node);
        ProcessInstance<String> instance = new DefaultProcessInstance<>(definition);
        instance.execute(null);
    }

}
