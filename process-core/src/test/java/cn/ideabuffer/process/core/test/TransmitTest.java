package cn.ideabuffer.process.core.test;

import cn.ideabuffer.process.core.*;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.nodes.TransmissionNode;
import cn.ideabuffer.process.core.nodes.builder.TransmissionNodeBuilder;
import cn.ideabuffer.process.core.test.processors.transmitter.TestTransmittableProcessor;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author sangjian.sj
 * @date 2020/03/10
 */
public class TransmitTest {

    @Test
    public void testTransmitNode() throws Exception {
//        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        Key<String> key = Contexts.newKey("k", String.class);
        Key<String> resultKey = Contexts.newKey("rk", String.class);
        TransmissionNode<String> node = TransmissionNodeBuilder.<String>newBuilder()
            .by(new TestTransmittableProcessor())
            .resultKey(resultKey)
            .readableKeys(key, resultKey)
            .build();
        node.thenApply((ctx, r) -> {
            assertEquals("hello", r);
            assertEquals("hello", ctx.get(resultKey));
            ctx.get(key);
            return r + " world";
        }).thenApply((ctx, r) -> {
            assertEquals("hello world", r);
            ctx.get(key);
            return r.length();
        }).thenAccept((ctx, r) -> assertEquals(11, (int)r));
        ProcessDefinition<String> definition = ProcessDefinitionBuilder.<String>newBuilder()
            .declaringKeys(key, resultKey)
            .addProcessNodes(node)
            .build();
        ProcessInstance<String> instance = definition.newInstance();
        Context context = Contexts.newContext();

        instance.execute(context);
        definition.destroy();
    }

}
