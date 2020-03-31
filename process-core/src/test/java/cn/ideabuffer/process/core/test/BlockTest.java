package cn.ideabuffer.process.core.test;

import cn.ideabuffer.process.core.*;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.test.nodes.TestBlockNode2;
import cn.ideabuffer.process.core.test.nodes.TestBlockNode1;
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
        Key<Integer> key = Contexts.newKey("k", int.class);
        context.put(key, 50);
        instance.execute(context);
        logger.info("after execute, k:{}", context.get(key));
    }
}
