package cn.ideabuffer.process.core.test;

import cn.ideabuffer.process.core.DefaultProcessDefinition;
import cn.ideabuffer.process.core.ProcessDefinition;
import cn.ideabuffer.process.core.ProcessInstance;
import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.nodes.ProcessNode;
import cn.ideabuffer.process.core.nodes.builder.ProcessNodeBuilder;
import cn.ideabuffer.process.core.nodes.wrapper.WrapperHandler;
import cn.ideabuffer.process.core.nodes.wrapper.WrapperProxy;
import cn.ideabuffer.process.core.status.ProcessStatus;
import cn.ideabuffer.process.core.test.handlers.TestHandler1;
import cn.ideabuffer.process.core.test.handlers.TestHandler2;
import cn.ideabuffer.process.core.test.nodes.TestProcessor1;
import cn.ideabuffer.process.core.test.nodes.TestProcessor2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author sangjian.sj
 * @date 2021/06/13
 */
public class WrapperTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(WrapperTest.class);

    @Test
    public void testWrapped() throws Exception {
        Key<Integer> resultKey = new Key<>("resultKey", int.class);
        ProcessDefinition<Integer> definition = new DefaultProcessDefinition<>(resultKey);

        Key<Integer> key = Contexts.newKey("k", int.class);

        AtomicInteger counter = new AtomicInteger();
        AtomicInteger processor1Order = new AtomicInteger();
        AtomicInteger processor2Order = new AtomicInteger();
        ProcessNode<Integer> node1 = ProcessNodeBuilder.<Integer>newBuilder()
            // 设置返回结果key
            .resultKey(resultKey)
            // 设置Processor
            .by(context -> {
                processor1Order.set(counter.incrementAndGet());
                LOGGER.info("in node1");
                return 1;
            })
            // 注册可读的key
            .readableKeys(key)
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
            // 注册可读的key
            .readableKeys(key)
            .build();

        AtomicInteger handler1BeforeOrder = new AtomicInteger();
        AtomicInteger handler1AfterReturningOrder = new AtomicInteger();
        AtomicInteger handler1AfterThrowingOrder = new AtomicInteger();

        AtomicInteger handler2BeforeOrder = new AtomicInteger();
        AtomicInteger handler2AfterReturningOrder = new AtomicInteger();
        AtomicInteger handler2AfterThrowingOrder = new AtomicInteger();

        definition
            // 注册执行节点
            .addProcessNodes(
                WrapperProxy.wrap(node1, new WrapperHandler<Integer>() {
                    @Override
                    public void before(@NotNull Context context) {
                        handler1BeforeOrder.set(counter.incrementAndGet());
                    }

                    @Override
                    public void afterReturning(@NotNull Context context, @Nullable Integer result,
                        @NotNull ProcessStatus status) {
                        handler1AfterReturningOrder.set(counter.incrementAndGet());
                    }

                    @Override
                    public void afterThrowing(@NotNull Context context, @NotNull Throwable t) {
                        handler1AfterThrowingOrder.set(counter.incrementAndGet());
                    }
                }, new WrapperHandler<Integer>() {
                    @Override
                    public void before(@NotNull Context context) {
                        handler2BeforeOrder.set(counter.incrementAndGet());
                    }

                    @Override
                    public void afterReturning(@NotNull Context context, @Nullable Integer result,
                        @NotNull ProcessStatus status) {
                        handler2AfterReturningOrder.set(counter.incrementAndGet());
                    }

                    @Override
                    public void afterThrowing(@NotNull Context context, @NotNull Throwable t) {
                        handler2AfterThrowingOrder.set(counter.incrementAndGet());
                    }
                }),
                node2);
        ProcessInstance<Integer> instance = definition.newInstance();
        Context context = Contexts.newContext();
        context.put(key, 0);

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

}
