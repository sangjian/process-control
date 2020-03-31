package cn.ideabuffer.process.core.test;

import cn.ideabuffer.process.core.DefaultProcessDefinition;
import cn.ideabuffer.process.core.DefaultProcessInstance;
import cn.ideabuffer.process.core.ProcessDefinition;
import cn.ideabuffer.process.core.ProcessInstance;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.test.nodes.transmitter.TestTransmittableNode;
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
