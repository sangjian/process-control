package cn.ideabuffer.process.test.nodes;

import cn.ideabuffer.process.block.Block;
import cn.ideabuffer.process.context.Context;
import cn.ideabuffer.process.context.Contexts;
import cn.ideabuffer.process.context.Key;
import cn.ideabuffer.process.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.status.ProcessStatus;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class TestBlockNode1 extends AbstractExecutableNode {

    @Override
    protected ProcessStatus doExecute(Context context) throws Exception {
        Block block = context.getBlock();
        Key<Integer> key = Contexts.newKey("k", int.class);
        int k = block.get(key, 0);
        logger.info("before put, k = {}", k);
        block.put(key, 100);
        logger.info("after put, k = {}", block.get(key));
        return ProcessStatus.PROCEED;
    }
}
