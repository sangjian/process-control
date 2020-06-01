package cn.ideabuffer.process.core.test;

import cn.ideabuffer.process.core.DefaultProcessDefinition;
import cn.ideabuffer.process.core.DefaultProcessInstance;
import cn.ideabuffer.process.core.ProcessDefinition;
import cn.ideabuffer.process.core.ProcessInstance;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.nodes.TransmitNode;
import cn.ideabuffer.process.core.test.nodes.transmitter.TestTransmittableProcessor;
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
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        TransmitNode<String> node = new TransmitNode<>(context -> "hello");
        node.thenApply((ctx, r) -> {
            assertEquals("hello", r);
            return r + " world";
        }).thenApply((ctx, r) -> {
            assertEquals("hello world", r);
            return r.length();
        }).thenAccept((ctx, r) -> assertEquals(11, (int)r));
        definition.addProcessNodes(node);
        ProcessInstance<String> instance = definition.newInstance();
        Context context = Contexts.newContext();

        instance.execute(context);
    }

}
