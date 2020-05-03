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

/**
 * @author sangjian.sj
 * @date 2020/03/10
 */
public class TransmitTest {

    @Test
    public void testTransmitNode() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        TransmitNode<String> node = new TransmitNode<>(new TestTransmittableProcessor());
        node.thenApply((ctx, r) -> {
            System.out.println(r);
            return r + " world";
        }).thenApply((ctx, r) -> {
            System.out.println(r);
            return r.length();
        }).thenAccept((ctx, r) -> System.out.println(r));
        definition.addProcessNodes(node);
        ProcessInstance<String> instance = definition.newInstance();
        Context context = Contexts.newContext();

        instance.execute(context);
    }

}
