package cn.ideabuffer.process.test;

import cn.ideabuffer.process.*;
import cn.ideabuffer.process.context.Context;
import cn.ideabuffer.process.context.ContextKey;
import cn.ideabuffer.process.context.Contexts;
import cn.ideabuffer.process.test.nodes.TestBlockNode1;
import cn.ideabuffer.process.test.nodes.TestBlockNode2;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sangjian.sj
 * @date 2020/03/25
 */
public class BlockTest {

    private static final Logger logger = LoggerFactory.getLogger(BlockTest.class);

    @Test
    public void testContextBlockIsolate() throws Exception {
        ProcessDefinition<Void> definition = new DefaultProcessDefinition<>();
        definition.addProcessNode(new TestBlockNode1(), new TestBlockNode2());

        ProcessInstance<Void> instance = new DefaultProcessInstance<>(definition);
        Context context = Contexts.newContext();
        ContextKey<Integer> key = Contexts.newKey("k", int.class);
        context.put(key, 50);
        instance.execute(context);
        logger.info("after execute, k:{}", context.get(key));
    }
}
