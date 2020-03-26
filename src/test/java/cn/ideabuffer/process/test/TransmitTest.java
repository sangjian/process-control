package cn.ideabuffer.process.test;

import cn.ideabuffer.process.*;
import cn.ideabuffer.process.context.Context;
import cn.ideabuffer.process.context.Contexts;
import cn.ideabuffer.process.test.nodes.transmitter.TestTransmittableNode;
import org.junit.Test;

/**
 * @author sangjian.sj
 * @date 2020/03/10
 */
public class TransmitTest {

    @Test
    public void testTransmitNode() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        TestTransmittableNode node = new TestTransmittableNode();
        node.thenApply((ctx, r) -> {
            System.out.println(r);
            return r + " world";
        }).thenApply((ctx, r) -> {
            System.out.println(r);
            return r.length();
        }).thenAccept((ctx, r) -> System.out.println(r));
        definition.addProcessNode(node);
        ProcessInstance<String> instance = new DefaultProcessInstance<>(definition);
        Context context = Contexts.newContext();

        instance.execute(context);
    }

}
