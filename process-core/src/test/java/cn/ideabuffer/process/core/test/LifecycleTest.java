package cn.ideabuffer.process.core.test;

import cn.ideabuffer.process.core.*;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.nodes.builder.ProcessNodeBuilder;
import cn.ideabuffer.process.core.test.processors.lifecycle.TestLifecycleProcessor1;
import cn.ideabuffer.process.core.test.processors.lifecycle.TestLifecycleProcessor2;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author sangjian.sj
 * @date 2021/06/27
 */
public class LifecycleTest {

    /**
     * 执行次数判断
     *
     * @throws Exception
     */
    @Test
    public void testLifecycle1() throws Exception {
        Key<String> resultKey = new Key<>("resultKey", String.class);
//        ProcessDefinition<String> definition = new DefaultProcessDefinition<>(resultKey);

        Processor<String> processor1 = new TestLifecycleProcessor1();
        Processor<String> processor2 = new TestLifecycleProcessor2();
        ProcessDefinition<String> definition = ProcessDefinitionBuilder.<String>newBuilder()
            .declaringKeys(resultKey)
            .resultHandler(context -> context.get(resultKey))
            // 注册执行节点
            .addProcessNodes(
                ProcessNodeBuilder.<String>newBuilder()
                    // 设置返回结果key
                    .resultKey(resultKey)
                    // 设置Processor
                    .by(processor1)
                    // 返回条件
                    .returnOn(result -> true)
                    .build(),
                ProcessNodeBuilder.<String>newBuilder()
                    .resultKey(resultKey)
                    .by(processor2)
                    .returnOn(result -> false)
                    .build())
            .build();
        ProcessInstance<String> instance = definition.newInstance();
        Context context = Contexts.newContext();

        instance.execute(context);
        definition.destroy();
        // 判断执行次数
        assertEquals(1L, ((TestLifecycleProcessor1)processor1).getInitializeCounter().longValue());
        assertEquals(1L, ((TestLifecycleProcessor1)processor1).getDestroyCounter().longValue());

        assertEquals(1L, ((TestLifecycleProcessor2)processor2).getInitializeCounter().longValue());
        assertEquals(1L, ((TestLifecycleProcessor2)processor2).getDestroyCounter().longValue());
    }

}
