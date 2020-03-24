package cn.ideabuffer.process.test;

import cn.ideabuffer.process.*;
import cn.ideabuffer.process.test.nodes.transmitter.TestTransmittableNode;
import org.junit.Test;

/**
 * @author sangjian.sj
 * @date 2020/03/10
 */
public class TransmitTest {

    @Test
    public void testTransmitNode() throws Exception {
        ProcessDefine<String> define = new DefaultProcessDefine<>();
        TestTransmittableNode node = new TestTransmittableNode();
        node.thenApply((ctx, r) -> {
            System.out.println(r);
            return r + " world";
        }).thenApply((ctx, r) -> {
            System.out.println(r);
            return r.length();
        }).thenAccept((ctx, r) -> System.out.println(r));
        define.addProcessNode(node);
        ProcessInstance<String> instance = new DefaultProcessInstance<>(define);
        Context context = new DefaultContext();

        instance.execute(context);
    }

}
