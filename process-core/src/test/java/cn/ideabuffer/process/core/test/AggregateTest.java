package cn.ideabuffer.process.core.test;

import cn.ideabuffer.process.core.DefaultProcessDefinition;
import cn.ideabuffer.process.core.DefaultProcessInstance;
import cn.ideabuffer.process.core.ProcessDefinition;
import cn.ideabuffer.process.core.ProcessInstance;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.nodes.*;
import cn.ideabuffer.process.core.nodes.aggregate.Aggregators;
import cn.ideabuffer.process.core.nodes.aggregate.DefaultDistributeAggregatableNode;
import cn.ideabuffer.process.core.nodes.aggregate.DistributeAggregator;
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

        // 创建单元化聚合节点
        UnitAggregatableNode<List<String>> node = Nodes.newUnitAggregatableNode();
        Executor executor = Executors.newFixedThreadPool(3);

        node.aggregator(Aggregators.newParallelUnitAggregator(executor, new ArrayListMerger<>())).aggregate(
            new TestListMergeableNode1(), new TestListMergeableNode2())
            // 链式结果处理
            .thenApply(((ctx, result) -> {
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

        // 创建通用聚合节点
        GenericAggregatableNode<String, List<String>> node = Nodes.newGenericAggregatableNode();
        Executor executor = Executors.newFixedThreadPool(3);
        node.aggregator(Aggregators.newParallelGenericAggregator(executor, new TestStringListMerger())).aggregate(
            new TestStringMergeableNode1(), new TestStringMergeableNode2())
            // 链式结果处理
            .thenApply(((ctx, result) -> {
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

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        logger.info("ehhehehe");
        Future<String> future = new FutureTask<>(() -> {
            logger.info("in thread:{}", Thread.currentThread().getName());
            Thread.sleep(5000);
            logger.info("in thread:{}", Thread.currentThread().getName());
            return "hello";
        });
        ((FutureTask<String>)future).run();
        logger.info(future.get(3, TimeUnit.SECONDS));
    }

}
