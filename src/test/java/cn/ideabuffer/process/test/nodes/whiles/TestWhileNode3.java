package cn.ideabuffer.process.test.nodes.whiles;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.block.Block;
import cn.ideabuffer.process.nodes.AbstractExecutableNode;

/**
 * @author sangjian.sj
 * @date 2020/01/20
 */
public class TestWhileNode3 extends AbstractExecutableNode {
    @Override
    public boolean doExecute(Context context) throws Exception {
        Block block = context.getBlock();
        int k = block.get("k", 0);
        block.put("k", ++k);
        if (k == 4 && block.allowContinue()) {
            block.put("k", 1);
            block.doContinue();
        }
        logger.info("k = " + k);
        Thread.sleep(1000);
        return false;
    }
}
