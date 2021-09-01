package cn.ideabuffer.process.core.test;

import cn.ideabuffer.process.core.ProcessDefinition;
import cn.ideabuffer.process.core.ProcessDefinitionBuilder;
import cn.ideabuffer.process.core.ProcessInstance;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.nodes.NodeGroup;
import cn.ideabuffer.process.core.nodes.builder.NodeGroupBuilder;
import cn.ideabuffer.process.core.nodes.builder.ProcessNodeBuilder;
import cn.ideabuffer.process.core.test.processors.TestProcessor1;
import cn.ideabuffer.process.core.test.processors.TestProcessor2;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NodeGroupTest {

    @Test
    public void resultTest() throws Exception {
        Key<String> resultKey = new Key<>("resultKey", String.class);

        Key<String> key1 = Contexts.newKey("k1", String.class);
        Key<String> key2 = Contexts.newKey("k2", String.class);
        NodeGroup<String> group = NodeGroupBuilder.<String>newBuilder()
            .addNodes(
                ProcessNodeBuilder.<String>newBuilder()
                    // 设置返回结果key
                    .resultKey(key1)
                    // 设置Processor
                    .by(context -> "hello")
                    .build())
                .addNodes(
                ProcessNodeBuilder.<String>newBuilder()
                    .resultKey(key2)
                    .by(context -> " world")
                    .build())
            .resultHandler(context -> context.get(key1) + context.get(key2))
            .resultKey(resultKey)
            .build();

        ProcessDefinition<String> definition = ProcessDefinitionBuilder.<String>newBuilder()
            .declaringKeys(resultKey, key1, key2)
            .resultHandler(context -> context.get(resultKey))
            // 注册执行节点
            .addGroup(group)
            .build();
        ProcessInstance<String> instance = definition.newInstance();
        Context context = Contexts.newContext();

        String result = instance.process(context);
        // 输出执行结果
        assertEquals("hello world", result);
        definition.destroy();
    }

}
