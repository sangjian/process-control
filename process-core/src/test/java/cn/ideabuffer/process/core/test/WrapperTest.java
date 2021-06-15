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
import cn.ideabuffer.process.core.nodes.wrapper.WrapperProxy;
import cn.ideabuffer.process.core.test.handlers.TestHandler1;
import cn.ideabuffer.process.core.test.handlers.TestHandler2;
import cn.ideabuffer.process.core.test.nodes.TestProcessor1;
import cn.ideabuffer.process.core.test.nodes.TestProcessor2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

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

        ProcessNode<Integer> node1 = ProcessNodeBuilder.<Integer>newBuilder()
            // 设置返回结果key
            .resultKey(resultKey)
            // 设置Processor
            .by(context -> {
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
                LOGGER.info("in node2");
                return 2;
            })
            // 注册可读的key
            .readableKeys(key)
            .build();

        definition
            // 注册执行节点
            .addProcessNodes(
                WrapperProxy.wrap(node1, new TestHandler1(), new TestHandler2()),
                node2);
        ProcessInstance<Integer> instance = definition.newInstance();
        Context context = Contexts.newContext();
        context.put(key, 0);

        instance.execute(context);
        // 输出执行结果
        assertEquals(2L, (long)instance.getResult());
    }

}
