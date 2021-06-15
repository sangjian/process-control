package cn.ideabuffer.process.core.test;

import cn.ideabuffer.process.core.*;
import cn.ideabuffer.process.core.block.Block;
import cn.ideabuffer.process.core.block.BlockFacade;
import cn.ideabuffer.process.core.block.InnerBlock;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.nodes.ProcessNode;
import cn.ideabuffer.process.core.test.nodes.TestBlockProcessor2;
import cn.ideabuffer.process.core.test.nodes.TestBlockProcessor1;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

/**
 * @author sangjian.sj
 * @date 2020/03/25
 */
public class BlockTest {

    private static final Logger logger = LoggerFactory.getLogger(BlockTest.class);

    /**
     * 测试context与block隔离
     * @throws Exception
     */
    @Test
    public void testContextBlockIsolate() throws Exception {
        ProcessDefinition<Void> definition = new DefaultProcessDefinition<>();
        definition.addProcessNodes(new ProcessNode<>(new TestBlockProcessor1()), new ProcessNode<>(new TestBlockProcessor2()));

        ProcessInstance<Void> instance = definition.newInstance();
        Context context = Contexts.newContext();
        Key<Integer> key = Contexts.newKey("k", int.class);
        context.put(key, 50);
        // 创建新的block，实现block与congtext隔离
        Context wrapper = Contexts.wrap(context, new BlockFacade(new InnerBlock(context.getBlock())));
        instance.execute(wrapper);
        // Processor操作block不影响context
        assertEquals(50, (int)context.get(key));
        logger.info("after execute, k in Context:{}", context.get(key));
    }
}
