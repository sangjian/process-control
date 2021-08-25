package cn.ideabuffer.process.core.test;

import cn.ideabuffer.process.core.DefaultProcessDefinition;
import cn.ideabuffer.process.core.ProcessDefinition;
import cn.ideabuffer.process.core.ProcessDefinitionBuilder;
import cn.ideabuffer.process.core.ProcessInstance;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.nodes.Nodes;
import cn.ideabuffer.process.core.nodes.ProcessNode;
import cn.ideabuffer.process.core.nodes.builder.ProcessNodeBuilder;
import cn.ideabuffer.process.core.nodes.builder.WhileNodeBuilder;
import cn.ideabuffer.process.core.nodes.condition.WhileConditionNode;
import cn.ideabuffer.process.core.processors.wrapper.StatusWrapperHandler;
import cn.ideabuffer.process.core.processors.wrapper.WrapperHandler;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;

/**
 * @author sangjian.sj
 * @date 2021/06/13
 */
public class WrapperTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(WrapperTest.class);

    @Test
    public void testDefaultProcessorProxy() throws Exception {
        Key<Integer> resultKey = new Key<>("resultKey", int.class);
//        ProcessDefinition<Integer> definition = new DefaultProcessDefinition<>(resultKey);

        AtomicInteger counter = new AtomicInteger();
        AtomicInteger processor1Order = new AtomicInteger();
        AtomicInteger processor2Order = new AtomicInteger();

        AtomicInteger handler1BeforeOrder = new AtomicInteger();
        AtomicInteger handler1AfterReturningOrder = new AtomicInteger();
        AtomicInteger handler1AfterThrowingOrder = new AtomicInteger();

        AtomicInteger handler2BeforeOrder = new AtomicInteger();
        AtomicInteger handler2AfterReturningOrder = new AtomicInteger();
        AtomicInteger handler2AfterThrowingOrder = new AtomicInteger();

        ProcessNode<Integer> node1 = ProcessNodeBuilder.<Integer>newBuilder()
            // 设置返回结果key
            .resultKey(resultKey)
            // 设置Processor
            .by(context -> {
                processor1Order.set(counter.incrementAndGet());
                LOGGER.info("in node1");
                return 1;
            })
            .wrap(new WrapperHandler<Integer>() {
                @Override
                public void before(@NotNull Context context) {
                    handler1BeforeOrder.set(counter.incrementAndGet());
                    LOGGER.info("in handler1 before");
                }

                @Override
                public Integer afterReturning(@NotNull Context context, @Nullable Integer result) {
                    handler1AfterReturningOrder.set(counter.incrementAndGet());
                    LOGGER.info("in handler1 afterReturning, result:{}", result);
                    return result;
                }

                @Override
                public void afterThrowing(@NotNull Context context, @NotNull Throwable t) throws Throwable{
                    handler1AfterThrowingOrder.set(counter.incrementAndGet());
                    LOGGER.info("in handler1 afterThrowing");
                }
            })
            .wrap(new WrapperHandler<Integer>() {
                @Override
                public void before(@NotNull Context context) {
                    handler2BeforeOrder.set(counter.incrementAndGet());
                    LOGGER.info("in handler2 before");
                }

                @Override
                public Integer afterReturning(@NotNull Context context, @Nullable Integer result) {
                    handler2AfterReturningOrder.set(counter.incrementAndGet());
                    LOGGER.info("in handler2 afterReturning, result:{}", result);
                    return result;
                }

                @Override
                public void afterThrowing(@NotNull Context context, @NotNull Throwable t) throws Throwable {
                    handler2AfterThrowingOrder.set(counter.incrementAndGet());
                    LOGGER.info("in handler2 afterThrowing");
                }
            })
            .build();
        ProcessNode<Integer> node2 = ProcessNodeBuilder.<Integer>newBuilder()
            // 设置返回结果key
            .resultKey(resultKey)
            // 设置Processor
            .by(context -> {
                processor2Order.set(counter.incrementAndGet());
                LOGGER.info("in node2");
                return 2;
            })
            .build();



        ProcessDefinition<Integer> definition = ProcessDefinitionBuilder.<Integer>newBuilder()
            .declaringKeys(resultKey)
            .resultHandler(context -> context.get(resultKey))
            // 注册执行节点
            .addProcessNodes(node1, node2)
            .build();
        ProcessInstance<Integer> instance = definition.newInstance();
        Context context = Contexts.newContext();

        instance.execute(context);
        // 输出执行结果
        assertEquals(2L, (long)instance.getResult());
        // 保证顺序正确
        assertEquals(1L, (long)handler1BeforeOrder.get());
        assertEquals(2L, (long)handler2BeforeOrder.get());
        assertEquals(3L, (long)processor1Order.get());
        assertEquals(4L, (long)handler2AfterReturningOrder.get());
        assertEquals(0L, (long)handler2AfterThrowingOrder.get());
        assertEquals(5L, (long)handler1AfterReturningOrder.get());
        assertEquals(0L, (long)handler1AfterThrowingOrder.get());
    }

    @Test
    public void testDefaultProcessorProxyThrowing() throws Exception {
        Key<Integer> resultKey = new Key<>("resultKey", int.class);
//        ProcessDefinition<Integer> definition = new DefaultProcessDefinition<>(resultKey);

        AtomicInteger counter = new AtomicInteger();
        AtomicInteger processor1Order = new AtomicInteger();
        AtomicInteger processor2Order = new AtomicInteger();

        AtomicInteger handler1BeforeOrder = new AtomicInteger();
        AtomicInteger handler1AfterReturningOrder = new AtomicInteger();
        AtomicInteger handler1AfterThrowingOrder = new AtomicInteger();

        AtomicInteger handler2BeforeOrder = new AtomicInteger();
        AtomicInteger handler2AfterReturningOrder = new AtomicInteger();
        AtomicInteger handler2AfterThrowingOrder = new AtomicInteger();

        ProcessNode<Integer> node1 = ProcessNodeBuilder.<Integer>newBuilder()
            // 设置返回结果key
            .resultKey(resultKey)
            // 设置Processor
            .by(context -> {
                processor1Order.set(counter.incrementAndGet());
                LOGGER.info("in node1");
                throw new RuntimeException("test throwing");
            })
            .wrap(new WrapperHandler<Integer>() {
                @Override
                public void before(@NotNull Context context) {
                    handler1BeforeOrder.set(counter.incrementAndGet());
                    LOGGER.info("in handler1 before");
                }

                @Override
                public Integer afterReturning(@NotNull Context context, @Nullable Integer result) {
                    handler1AfterReturningOrder.set(counter.incrementAndGet());
                    LOGGER.info("in handler1 afterReturning, result:{}", result);
                    return result;
                }

                @Override
                public void afterThrowing(@NotNull Context context, @NotNull Throwable t) throws Throwable {
                    handler1AfterThrowingOrder.set(counter.incrementAndGet());
                    LOGGER.info("in handler1 afterThrowing", t);
                    throw t;
                }
            })
            .wrap(new WrapperHandler<Integer>() {
                @Override
                public void before(@NotNull Context context) {
                    handler2BeforeOrder.set(counter.incrementAndGet());
                    LOGGER.info("in handler2 before");
                }

                @Override
                public Integer afterReturning(@NotNull Context context, @Nullable Integer result) {
                    handler2AfterReturningOrder.set(counter.incrementAndGet());
                    LOGGER.info("in handler2 afterReturning, result:{}", result);
                    return result;
                }

                @Override
                public void afterThrowing(@NotNull Context context, @NotNull Throwable t) throws Throwable {
                    handler2AfterThrowingOrder.set(counter.incrementAndGet());
                    LOGGER.info("in handler2 afterThrowing", t);
                    throw t;
                }
            })
            .build();
        ProcessNode<Integer> node2 = ProcessNodeBuilder.<Integer>newBuilder()
            // 设置返回结果key
            .resultKey(resultKey)
            // 设置Processor
            .by(context -> {
                processor2Order.set(counter.incrementAndGet());
                LOGGER.info("in node2");
                return 2;
            })
            .build();



        ProcessDefinition<Integer> definition = ProcessDefinitionBuilder.<Integer>newBuilder()
            .declaringKeys(resultKey)
            .resultHandler(context -> context.get(resultKey))
            // 注册执行节点
            .addProcessNodes(node1, node2)
            .build();
        ProcessInstance<Integer> instance = definition.newInstance();
        Context context = Contexts.newContext();

        try {
            instance.execute(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 保证顺序正确
        assertEquals(1L, (long)handler1BeforeOrder.get());
        assertEquals(2L, (long)handler2BeforeOrder.get());
        assertEquals(3L, (long)processor1Order.get());
        assertEquals(0L, (long)handler2AfterReturningOrder.get());
        assertEquals(4L, (long)handler2AfterThrowingOrder.get());
        assertEquals(0L, (long)handler1AfterReturningOrder.get());
        assertEquals(5L, (long)handler1AfterThrowingOrder.get());
    }

    @Test
    public void testWhileProcessorProxy() throws Exception {
        Key<Integer> resultKey = new Key<>("resultKey", int.class);
//        ProcessDefinition<Integer> definition = new DefaultProcessDefinition<>(resultKey);

        AtomicInteger counter = new AtomicInteger();
        // 循环次数
        AtomicInteger whileCounter = new AtomicInteger();
        AtomicInteger processor1Order = new AtomicInteger();
        AtomicInteger processor2Order = new AtomicInteger();

        AtomicInteger handler1BeforeOrder = new AtomicInteger();
        AtomicInteger handler1AfterReturningOrder = new AtomicInteger();
        AtomicInteger handler1AfterThrowingOrder = new AtomicInteger();

        AtomicInteger handler2BeforeOrder = new AtomicInteger();
        AtomicInteger handler2AfterReturningOrder = new AtomicInteger();
        AtomicInteger handler2AfterThrowingOrder = new AtomicInteger();

        WhileConditionNode whileNode = WhileNodeBuilder.newBuilder().processOn(context -> whileCounter.getAndIncrement() < 5)
            .wrap(new StatusWrapperHandler() {
                @Override
                public void before(@NotNull Context context) {
                    handler1BeforeOrder.set(counter.incrementAndGet());
                    LOGGER.info("in handler1 before, handler1BeforeOrder:{}", handler1BeforeOrder.get());
                }

                @NotNull
                @Override
                public ProcessStatus afterReturning(@NotNull Context context, @NotNull ProcessStatus status) {
                    handler1AfterReturningOrder.set(counter.incrementAndGet());
                    LOGGER.info("in handler1 afterReturning, handler1AfterReturningOrder:{}, status:{}", handler1AfterReturningOrder.get(), status);
                    return status;
                }

                @Override
                public void afterThrowing(@NotNull Context context, @NotNull Throwable t)
                    throws Throwable {
                    handler1AfterThrowingOrder.set(counter.incrementAndGet());
                    LOGGER.info("in handler1 afterThrowing, handler1AfterThrowingOrder:{}", handler1AfterThrowingOrder.get());
                }
            })
            .wrap(new StatusWrapperHandler() {
                @Override
                public void before(@NotNull Context context) {
                    handler2BeforeOrder.set(counter.incrementAndGet());
                    LOGGER.info("in handler2 before, handler2BeforeOrder:{}", handler2BeforeOrder.get());
                }

                @NotNull
                @Override
                public ProcessStatus afterReturning(@NotNull Context context, @NotNull ProcessStatus status) {
                    handler2AfterReturningOrder.set(counter.incrementAndGet());
                    LOGGER.info("in handler2 afterReturning, handler2AfterReturningOrder:{}, status:{}", handler2AfterReturningOrder.get(), status);
                    return status;
                }

                @Override
                public void afterThrowing(@NotNull Context context, @NotNull Throwable t)
                    throws Throwable {
                    handler2AfterThrowingOrder.set(counter.incrementAndGet());
                    LOGGER.info("in handler2 afterThrowing, handler2AfterThrowingOrder:{}", handler2AfterThrowingOrder.get());
                }
            })
            .then(
                Nodes.newBranch(
                    ProcessNodeBuilder.<Integer>newBuilder()
                        .by(context -> {
                            processor1Order.set(counter.incrementAndGet());
                            LOGGER.info("in node1, processor1Order:{}", processor1Order.get());
                            return 1;
                        })
                        .build(),
                    ProcessNodeBuilder.<Integer>newBuilder()
                        .by(context -> {
                            processor2Order.set(counter.incrementAndGet());
                            LOGGER.info("in node2, processor2Order:{}", processor2Order.get());
                            return 2;
                        })
                        .build()
                )
            )
            .build();


        ProcessDefinition<Integer> definition = ProcessDefinitionBuilder.<Integer>newBuilder()
            .declaringKeys(resultKey)
            .resultHandler(context -> context.get(resultKey))
            // 注册执行节点
            .addWhile(whileNode)
            .build();
        ProcessInstance<Integer> instance = definition.newInstance();
        Context context = Contexts.newContext();

        instance.execute(context);
        // 保证顺序正确
        assertEquals(1L, (long)handler1BeforeOrder.get());
        assertEquals(2L, (long)handler2BeforeOrder.get());
        assertEquals(11L, (long)processor1Order.get());
        assertEquals(12L, (long)processor2Order.get());
        assertEquals(6L, (long)whileCounter.get());
        assertEquals(13L, (long)handler2AfterReturningOrder.get());
        assertEquals(0L, (long)handler2AfterThrowingOrder.get());
        assertEquals(14L, (long)handler1AfterReturningOrder.get());
        assertEquals(0L, (long)handler1AfterThrowingOrder.get());
    }

}
