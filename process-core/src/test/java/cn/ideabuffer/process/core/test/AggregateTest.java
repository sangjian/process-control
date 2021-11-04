package cn.ideabuffer.process.core.test;

import cn.ideabuffer.process.core.*;
import cn.ideabuffer.process.core.aggregators.Aggregators;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.nodes.DistributeMergeableNode;
import cn.ideabuffer.process.core.nodes.GenericMergeableNode;
import cn.ideabuffer.process.core.nodes.aggregate.DistributeAggregatableNode;
import cn.ideabuffer.process.core.nodes.aggregate.GenericAggregatableNode;
import cn.ideabuffer.process.core.nodes.aggregate.UnitAggregatableNode;
import cn.ideabuffer.process.core.nodes.builder.*;
import cn.ideabuffer.process.core.nodes.merger.*;
import cn.ideabuffer.process.core.test.merger.TestStringListMerger;
import cn.ideabuffer.process.core.test.processors.aggregate.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * @author sangjian.sj
 * @date 2020/03/09
 */
public class AggregateTest {

    private static final Logger logger = LoggerFactory.getLogger(AggregateTest.class);

    @Test
    public void testUnitAggregateList() throws Exception {

        Key<List<String>> resultKey = Contexts.newKey("resultKey", List.class);
        Executor executor = Executors.newFixedThreadPool(3);

        GenericMergeableNode<List<String>> node1 = GenericMergeableNodeBuilder.<List<String>>newBuilder()
            .by(new TestListMergeNodeProcessor1())
            .build();
        GenericMergeableNode<List<String>> node2 = GenericMergeableNodeBuilder.<List<String>>newBuilder().by(
            new TestListMergeNodeProcessor2()).build();

        List<GenericMergeableNode<List<String>>> nodes = new ArrayList<>();
        nodes.add(node1);
        nodes.add(node2);

        // 创建单元化聚合节点
        UnitAggregatableNode<List<String>> node = UnitAggregatableNodeBuilder.<List<String>>newBuilder()
            .aggregator(
                Aggregators.newParallelUnitAggregator(executor, new ArrayListMerger<>())
            )
            .aggregate(nodes)
            .addListeners(new TestUnitAggregatableNodeListener1(), new TestUnitAggregatableNodeListener2())
            .resultKey(resultKey)
            .build();

        // 链式结果处理
        node.exceptionally(t -> {
            logger.info("in exceptionally.", t);
            return null;
        }).thenApply(((ctx, result) -> {
            boolean condition = result != null && result.size() == 2;
            condition = condition && result.stream().filter(r -> r.equals("test1") || r.equals("test2")).collect(
                Collectors.toList()).size() == 2;
            assertTrue("result.size must be 2", condition);
            logger.info("result:{}", result);
            return result.size();
        })).thenAccept((ctx, size) -> {
            assertEquals("size must be 2", 2, (int)size);
            logger.info("result:{}", size);
        });
        ProcessDefinition<List<String>> definition = ProcessDefinitionBuilder.<List<String>>newBuilder()
            .declaringKeys(resultKey)
            .resultHandler(context -> context.get(resultKey))
            .addAggregateNode(node)
            .build();

        ProcessInstance<List<String>> instance = definition.newInstance();
        Context context = Contexts.newContext();

        instance.execute(context);
    }

    @Test
    public void testGenericAggregateList() throws Exception {
        Key<List<String>> resultKey = Contexts.newKey("resultKey", List.class);
//        ProcessDefinition<List<String>> definition = new DefaultProcessDefinition<>(resultKey);

        Executor executor = Executors.newFixedThreadPool(3);

        GenericMergeableNode<String> node1 = GenericMergeableNodeBuilder.<String>newBuilder().by(
            new TestStringMergeNodeProcessor1())
            .build();
        GenericMergeableNode<String> node2 = GenericMergeableNodeBuilder.<String>newBuilder().by(
            new TestStringMergeNodeProcessor2())
            .build();

        List<GenericMergeableNode<String>> nodes = new ArrayList<>();
        nodes.add(node1);
        nodes.add(node2);

        // 创建通用聚合节点
        GenericAggregatableNode<String, List<String>> node
            = GenericAggregatableNodeBuilder.<String, List<String>>newBuilder().aggregator(
            Aggregators.newParallelGenericAggregator(executor, new TestStringListMerger()))
            .aggregate(nodes)
            .resultKey(resultKey)
            .build();
        // 链式结果处理
        node.thenApply(((ctx, result) -> {
            boolean condition = result != null && result.size() == 2;
            condition = condition && result.stream().filter(r -> r.equals("test1") || r.equals("test2")).collect(
                Collectors.toList()).size() == 2;
            assertTrue("result.size must be 2", condition);
            logger.info("result:{}", result);
            return result.size();
        })).thenAccept((ctx, size) -> {
            assertEquals("size must be 2", 2, (int)size);
            logger.info("result:{}", size);
        });
        ProcessDefinition<List<String>> definition = ProcessDefinitionBuilder.<List<String>>newBuilder()
            .declaringKeys(resultKey)
            .resultHandler(context -> context.get(resultKey))
            .addAggregateNode(node)
            .build();

        ProcessInstance<List<String>> instance = definition.newInstance();
        Context context = Contexts.newContext();

        instance.execute(context);
        List<String> result = instance.getResult();
        List<String> expectedList = new ArrayList<>();
        expectedList.add("test1");
        expectedList.add("test2");
        assertEquals(result, expectedList);
    }

    @Test
    public void testGenericAggregateListSerial() throws Exception {
        Key<List<String>> resultKey = Contexts.newKey("resultKey", List.class);
        Key<String> k1 = Contexts.newKey("k1", String.class);
        Key<String> k2 = Contexts.newKey("k2", String.class);

        GenericMergeableNode<String> node1 = GenericMergeableNodeBuilder.<String>newBuilder().by(
                new Processor<String>() {
                    @Override
                    public @Nullable String process(@NotNull Context context) throws Exception {
                        String s = context.get(k1);
                        return s;
                    }
                })
            .readableKeys(k1)
            .build();
        GenericMergeableNode<String> node2 = GenericMergeableNodeBuilder.<String>newBuilder().by(
                new Processor<String>() {
                    @Override
                    public @Nullable String process(@NotNull Context context) throws Exception {
                        String s = context.get(k2);
                        return s;
                    }
                })
            .readableKeys(k2)
            .build();

        List<GenericMergeableNode<String>> nodes = new ArrayList<>();
        nodes.add(node1);
        nodes.add(node2);

        // 创建通用聚合节点
        GenericAggregatableNode<String, List<String>> node
            = GenericAggregatableNodeBuilder.<String, List<String>>newBuilder().aggregator(
                Aggregators.newSerialGenericAggregator(new TestStringListMerger()))
            .aggregate(nodes)
//            .readableKeys(k1, k2)
            .resultKey(resultKey)
            .build();
        // 链式结果处理
        node.thenApply(((ctx, result) -> {
            boolean condition = result != null && result.size() == 2;
            condition = condition && result.stream().filter(r -> r.equals("test1") || r.equals("test2")).collect(
                Collectors.toList()).size() == 2;
            assertTrue("result.size must be 2", condition);
            logger.info("result:{}", result);
            return result.size();
        })).thenAccept((ctx, size) -> {
            assertEquals("size must be 2", 2, (int)size);
            logger.info("result:{}", size);
        });
        ProcessDefinition<List<String>> definition = ProcessDefinitionBuilder.<List<String>>newBuilder()
            .declaringKeys(resultKey, k1, k2)
            .resultHandler(context -> context.get(resultKey))
            .addAggregateNode(node)
            .build();

        ProcessInstance<List<String>> instance = definition.newInstance();
        Context context = Contexts.newContext();

        context.put(k1, "test1");
        context.put(k2, "test2");
        instance.execute(context);
        List<String> result = instance.getResult();
        List<String> expectedList = new ArrayList<>();
        expectedList.add("test1");
        expectedList.add("test2");
        assertEquals(expectedList, result);
    }

    @Test
    public void testGenericAggregateListSerialReturn() throws Exception {
        Key<List<String>> resultKey = Contexts.newKey("resultKey", List.class);
        Key<String> k1 = Contexts.newKey("k1", String.class);
        Key<String> k2 = Contexts.newKey("k2", String.class);

        GenericMergeableNode<String> node1 = GenericMergeableNodeBuilder.<String>newBuilder().by(
                new Processor<String>() {
                    @Override
                    public @Nullable String process(@NotNull Context context) throws Exception {
                        String s = context.get(k1);
                        return s;
                    }
                })
            .readableKeys(k1)
            .build();
        GenericMergeableNode<String> node2 = GenericMergeableNodeBuilder.<String>newBuilder().by(
                new Processor<String>() {
                    @Override
                    public @Nullable String process(@NotNull Context context) throws Exception {
                        String s = context.get(k2);
                        return s;
                    }
                })
            .readableKeys(k2)
            .build();

        List<GenericMergeableNode<String>> nodes = new ArrayList<>();
        nodes.add(node1);
        nodes.add(node2);

        // 创建通用聚合节点
        GenericAggregatableNode<String, List<String>> node
            = GenericAggregatableNodeBuilder.<String, List<String>>newBuilder().aggregator(
                Aggregators.newSerialGenericAggregator(new TestStringListMerger()))
            .aggregate(nodes)
//            .readableKeys(k1, k2)
            .resultKey(resultKey)
            .returnOn(new ReturnCondition<List<String>>() {
                @Override
                public boolean onCondition(@Nullable List<String> result) {
                    return result.size() == 2;
                }
            })
            .build();
        // 链式结果处理
        node.thenApply(((ctx, result) -> {
            boolean condition = result != null && result.size() == 2;
            condition = condition && result.stream().filter(r -> r.equals("test1") || r.equals("test2")).collect(
                Collectors.toList()).size() == 2;
            assertTrue("result.size must be 2", condition);
            logger.info("result:{}", result);
            return result.size();
        })).thenAccept((ctx, size) -> {
            assertEquals("size must be 2", 2, (int)size);
            logger.info("result:{}", size);
        });
        AtomicBoolean inProcessor = new AtomicBoolean(false);
        ProcessDefinition<List<String>> definition = ProcessDefinitionBuilder.<List<String>>newBuilder()
            .declaringKeys(resultKey, k1, k2)
            .resultHandler(context -> context.get(resultKey))
            .addAggregateNode(node)
            .addProcessNode(ProcessNodeBuilder.<String>newBuilder().by(new Processor<String>() {
                @Override
                public @Nullable String process(@NotNull Context context) throws Exception {
                    inProcessor.getAndSet(true);
                    return "";
                }
            })
                .build()
            )
            .build();

        ProcessInstance<List<String>> instance = definition.newInstance();
        Context context = Contexts.newContext();

        context.put(k1, "test1");
        context.put(k2, "test2");
        instance.execute(context);
        List<String> result = instance.getResult();
        List<String> expectedList = new ArrayList<>();
        expectedList.add("test1");
        expectedList.add("test2");
        assertEquals(expectedList, result);
        assertFalse(inProcessor.get());
    }

    @Test
    public void testTimeoutGenericAggregateList() throws Exception {
//        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();

        Executor executor = Executors.newFixedThreadPool(3);

        GenericMergeableNode<String> node1 = GenericMergeableNodeBuilder.<String>newBuilder().by(
            new TestStringMergeNodeTimeoutProcessor1()).build();
        GenericMergeableNode<String> node2 = GenericMergeableNodeBuilder.<String>newBuilder().by(
            new TestStringMergeNodeTimeoutProcessor2()).timeout(5000, TimeUnit.MILLISECONDS).build();

        List<GenericMergeableNode<String>> nodes = new ArrayList<>();
        nodes.add(node1);
        nodes.add(node2);

        // 创建通用聚合节点
        GenericAggregatableNode<String, List<String>> node
            = GenericAggregatableNodeBuilder.<String, List<String>>newBuilder().aggregator(
            Aggregators.newParallelGenericAggregator(executor, new TestStringListMerger(), 2000)).aggregate(nodes)
            .build();
        // 链式结果处理
        node.thenApply(((ctx, result) -> {
            boolean condition = result != null && result.size() == 1;
            condition = condition && result.stream().filter(r -> r.equals("test2")).collect(
                Collectors.toList()).size() == 1;
            assertTrue("result.size must be 1", condition);
            logger.info("result:{}", result);
            return result.size();
        })).thenAccept((ctx, size) -> {
            assertEquals("size must be 1", 1, (int)size);
            logger.info("result:{}", size);
        });
        ProcessDefinition<String> definition = ProcessDefinitionBuilder.<String>newBuilder()
            .addAggregateNode(node)
            .build();

        ProcessInstance<String> instance = definition.newInstance();
        Context context = Contexts.newContext();
        long start = System.currentTimeMillis();
        instance.execute(context);
        long costs = System.currentTimeMillis() - start;
        logger.warn("testTimeoutGenericAggregateList costs:{}", costs);
        assertTrue(costs < 3000);
    }

    @Test
    public void testGenericAggregateListWithException() throws Exception {
//        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();

        Executor executor = Executors.newFixedThreadPool(3);

        GenericMergeableNode<String> node1 = GenericMergeableNodeBuilder.<String>newBuilder()
            .by(context -> {
                throw new RuntimeException("test exception");
            }).build();
        GenericMergeableNode<String> node2 = GenericMergeableNodeBuilder.<String>newBuilder().by(
            new TestStringMergeNodeTimeoutProcessor2()).timeout(5000, TimeUnit.MILLISECONDS).build();

        List<GenericMergeableNode<String>> nodes = new ArrayList<>();
        nodes.add(node1);
        nodes.add(node2);

        // 创建通用聚合节点
        GenericAggregatableNode<String, List<String>> node
            = GenericAggregatableNodeBuilder.<String, List<String>>newBuilder().aggregator(
            Aggregators.newParallelGenericAggregator(executor, new TestStringListMerger(), 5000)).aggregate(nodes)
            .build();
        // 链式结果处理
        node.exceptionally(t -> {
            logger.warn("in exceptionally");
            return null;
        }).thenApply(((ctx, result) -> {
            assertEquals("test2", result.get(0));
            logger.info("result:{}", result);
            return result.size();
        })).thenAccept((ctx, size) -> {
            assertEquals("size must be 1", 1, (int)size);
            logger.info("result:{}", size);
        });
        ProcessDefinition<String> definition = ProcessDefinitionBuilder.<String>newBuilder()
            .addAggregateNode(node)
            .build();

        ProcessInstance<String> instance = definition.newInstance();
        Context context = Contexts.newContext();
        long start = System.currentTimeMillis();
        instance.execute(context);
        long costs = System.currentTimeMillis() - start;
        logger.warn("testGenericAggregateListWithException costs:{}", costs);
        assertTrue(costs < 2000);
    }

    @Test
    public void testIntSum() throws Exception {
//        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        GenericMergeableNode<Integer> node1 = GenericMergeableNodeBuilder.<Integer>newBuilder().by(new IntMergeNodeProcessor1())
            .build();
        GenericMergeableNode<Integer> node2 = GenericMergeableNodeBuilder.<Integer>newBuilder().by(new IntMergeNodeProcessor2())
            .build();

        List<GenericMergeableNode<Integer>> nodes = new ArrayList<>();
        nodes.add(node1);
        nodes.add(node2);

        UnitAggregatableNode<Integer> node = UnitAggregatableNodeBuilder.<Integer>newBuilder().aggregator(
            Aggregators.newSerialUnitAggregator(new IntSumMerger())).aggregate(nodes).build();

        node.thenApply(((ctx, result) -> {
            assertEquals(25, (int)result);
            return result;
        }));
        ProcessDefinition<String> definition = ProcessDefinitionBuilder.<String>newBuilder()
            .addAggregateNode(node)
            .build();

        ProcessInstance<String> instance = definition.newInstance();
        Context context = Contexts.newContext();

        instance.execute(context);
    }

    @Test
    public void testIntAvg() throws Exception {
//        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        GenericMergeableNode<Integer> node1 = GenericMergeableNodeBuilder.<Integer>newBuilder().by(new IntMergeNodeProcessor1())
            .build();
        GenericMergeableNode<Integer> node2 = GenericMergeableNodeBuilder.<Integer>newBuilder().by(new IntMergeNodeProcessor2())
            .build();

        List<GenericMergeableNode<Integer>> nodes = new ArrayList<>();
        nodes.add(node1);
        nodes.add(node2);

        UnitAggregatableNode<Integer> node = UnitAggregatableNodeBuilder.<Integer>newBuilder().aggregator(
            Aggregators.newSerialUnitAggregator(new IntAvgMerger())).aggregate(nodes).build();

        node.thenApply(((ctx, result) -> {
            assertEquals(12, (int)result);
            return result;
        }));
        ProcessDefinition<String> definition = ProcessDefinitionBuilder.<String>newBuilder()
            .addAggregateNode(node)
                .build();

        ProcessInstance<String> instance = definition.newInstance();
        Context context = Contexts.newContext();

        instance.execute(context);
    }

    @Test
    public void testDoubleSum() throws Exception {
//        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        GenericMergeableNode<Double> node1 = GenericMergeableNodeBuilder.<Double>newBuilder().by(new DoubleMergeNodeProcessor1())
            .build();
        GenericMergeableNode<Double> node2 = GenericMergeableNodeBuilder.<Double>newBuilder().by(new DoubleMergeNodeProcessor2())
            .build();

        List<GenericMergeableNode<Double>> nodes = new ArrayList<>();
        nodes.add(node1);
        nodes.add(node2);

        UnitAggregatableNode<Double> node = UnitAggregatableNodeBuilder.<Double>newBuilder().aggregator(
            Aggregators.newSerialUnitAggregator(new DoubleSumMerger())).aggregate(nodes).build();
        node.thenApply(((ctx, result) -> {
            assertEquals("double sum must be 25d", 25d, result, 0.001d);
            return result;
        }));
        ProcessDefinition<String> definition = ProcessDefinitionBuilder.<String>newBuilder()
            .addAggregateNode(node)
                .build();
        ProcessInstance<String> instance = new DefaultProcessInstance<>(definition);
        Context context = Contexts.newContext();

        instance.execute(context);
        //Thread.sleep(10000);
    }

    @Test
    public void testDoubleAvg() throws Exception {
//        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        GenericMergeableNode<Double> node1 = GenericMergeableNodeBuilder.<Double>newBuilder().by(new DoubleMergeNodeProcessor1())
            .build();
        GenericMergeableNode<Double> node2 = GenericMergeableNodeBuilder.<Double>newBuilder().by(new DoubleMergeNodeProcessor2())
            .build();

        List<GenericMergeableNode<Double>> nodes = new ArrayList<>();
        nodes.add(node1);
        nodes.add(node2);

        UnitAggregatableNode<Double> node = UnitAggregatableNodeBuilder.<Double>newBuilder().aggregator(
            Aggregators.newSerialUnitAggregator(new DoubleAvgMerger())).aggregate(nodes).build();
        node.thenApply(((ctx, result) -> {
            assertEquals("double avg must be 12.5d", 12.5d, result, 0.001d);
            return result;
        }));
        ProcessDefinition<String> definition = ProcessDefinitionBuilder.<String>newBuilder()
            .addAggregateNode(node)
                .build();
        ProcessInstance<String> instance = new DefaultProcessInstance<>(definition);
        Context context = Contexts.newContext();

        instance.execute(context);
        //Thread.sleep(10000);
    }

    @Test
    public void testIntArray() throws Exception {
        Key<int[]> resultKey = Contexts.newKey("resultKey", int[].class);
        GenericMergeableNode<int[]> node1 = GenericMergeableNodeBuilder.<int[]>newBuilder()
            .by(new IntArrayMergeNodeProcessor1())
            .build();
        GenericMergeableNode<int[]> node2 = GenericMergeableNodeBuilder.<int[]>newBuilder()
            .by(new IntArrayMergeNodeProcessor2())
            .build();
        List<GenericMergeableNode<int[]>> nodes = new ArrayList<>();
        nodes.add(node1);
        nodes.add(node2);
        UnitAggregatableNode<int[]> node = UnitAggregatableNodeBuilder.<int[]>newBuilder()
            .aggregator(
                Aggregators.newSerialUnitAggregator(new IntArrayMerger())
            )
            .aggregate(nodes)
            .resultKey(resultKey)
            .build();
        node.thenApply(((ctx, result) -> {
            assertArrayEquals(new int[] {1, 2, 6, 3, 5, 8}, result);
            return result;
        }));
        ProcessDefinition<int[]> definition = ProcessDefinitionBuilder.<int[]>newBuilder()
            .declaringKeys(resultKey)
            .addAggregateNode(node)
            .resultHandler(context -> context.get(resultKey))
            .build();
        ProcessInstance<int[]> instance = definition.newInstance();
        Context context = Contexts.newContext();

        int[] result = instance.process(context);
        assertArrayEquals(new int[] {1, 2, 6, 3, 5, 8}, result);
        //Thread.sleep(10000);
    }

    @Test
    public void testParallelDistributeAggregate() throws Exception {
        Executor executor = Executors.newFixedThreadPool(3);
        Key<Person> resultKey = Contexts.newKey("resultKey", Person.class);

        DistributeMergeableNode<Integer, Person> node1 = DistributeMergeableNodeBuilder.<Integer, Person>newBuilder().by(
            new TestDistributeMergeNodeProcessor1()).build();
        DistributeMergeableNode<String, Person> node2 = DistributeMergeableNodeBuilder.<String, Person>newBuilder().by(
            new TestDistributeMergeNodeProcessor2()).build();

        List<DistributeMergeableNode<?, Person>> nodes = new ArrayList<>();
        nodes.add(node1);
        nodes.add(node2);
        // 创建分布式聚合节点
        DistributeAggregatableNode<Person> node = DistributeAggregatableNodeBuilder.<Person>newBuilder()
            .aggregator(Aggregators.newParallelDistributeAggregator(executor, Person.class))
            .resultKey(resultKey)
            // 注册分布式可合并节点，并设置结果处理器
            .aggregate(nodes).build();
        node.thenApply((ctx, result) -> {
            logger.info("result:{}", result);
            Person p = new Person(30, "Flash");
            assertEquals(p, result);
            return result;
        });
        ProcessDefinition<Person> definition = ProcessDefinitionBuilder.<Person>newBuilder()
            .declaringKeys(resultKey)
            .resultHandler(context -> context.get(resultKey))
            // 注册分布式聚合节点
            .addDistributeAggregateNode(node)
                .build();
        ProcessInstance<Person> instance = definition.newInstance();
        Person result = instance.process(Contexts.newContext());
        Person person = new Person(30, "Flash");
        assertEquals(result, person);
    }

    @Test
    public void testSerialDistributeAggregate() throws Exception {
        Key<Person> resultKey = Contexts.newKey("resultKey", Person.class);

        DistributeMergeableNode<Integer, Person> node1 = DistributeMergeableNodeBuilder.<Integer, Person>newBuilder().by(
            new TestDistributeMergeNodeProcessor1()).build();
        DistributeMergeableNode<String, Person> node2 = DistributeMergeableNodeBuilder.<String, Person>newBuilder().by(
            new TestDistributeMergeNodeProcessor2()).build();

        List<DistributeMergeableNode<?, Person>> nodes = new ArrayList<>();
        nodes.add(node1);
        nodes.add(node2);
        // 创建分布式聚合节点
        DistributeAggregatableNode<Person> node = DistributeAggregatableNodeBuilder.<Person>newBuilder()
            .aggregator(Aggregators.newSerialDistributeAggregator(Person.class))
            .resultKey(resultKey)
            // 注册分布式可合并节点，并设置结果处理器
            .aggregate(nodes).build();
        node.thenApply((ctx, result) -> {
            logger.info("result:{}", result);
            Person p = new Person(30, "Flash");
            assertEquals(p, result);
            return result;
        });
        ProcessDefinition<Person> definition = ProcessDefinitionBuilder.<Person>newBuilder()
            .declaringKeys(resultKey)
            .resultHandler(context -> context.get(resultKey))
            // 注册分布式聚合节点
            .addDistributeAggregateNode(node)
            .build();
        ProcessInstance<Person> instance = definition.newInstance();
        Person result = instance.process(Contexts.newContext());
        Person person = new Person(30, "Flash");
        assertEquals(result, person);
    }

}
