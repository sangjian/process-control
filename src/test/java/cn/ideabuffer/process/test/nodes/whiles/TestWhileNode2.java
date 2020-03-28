package cn.ideabuffer.process.test.nodes.whiles;

import cn.ideabuffer.process.block.Block;
import cn.ideabuffer.process.context.Context;
import cn.ideabuffer.process.context.Contexts;
import cn.ideabuffer.process.context.Key;
import cn.ideabuffer.process.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.status.ProcessStatus;

/**
 * @author sangjian.sj
 * @date 2020/01/20
 */
public class TestWhileNode2 extends AbstractExecutableNode {
    @Override
    public ProcessStatus doExecute(Context context) throws Exception {
        Block block = context.getBlock();
        Key<Integer> key = Contexts.newKey("k", int.class);
        int k = block.get(key, 0);
        block.put(key, ++k);
        if (k == 4 && block.allowContinue()) {
            block.put(key, 1);
            block.doContinue();
        }
        logger.info("k = " + k);
        Thread.sleep(1000);
        return ProcessStatus.PROCEED;
    }
}
