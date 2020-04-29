package cn.ideabuffer.process.core.test.nodes.whiles;

import cn.ideabuffer.process.core.block.Block;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/01/20
 */
public class TestWhileBreakNode3 extends AbstractExecutableNode {
    @Override
    public ProcessStatus doExecute(Context context) throws Exception {
        Block block = context.getBlock();
        Key<Integer> key = Contexts.newKey("k", int.class);
        int k = block.get(key, 0);
        block.put(key, ++k);
        logger.info("k = " + k);
        if (k == 5 && block.allowBreak()) {
            block.put(key, 1);
            block.doBreak();
        }
        Thread.sleep(1000);
        return ProcessStatus.PROCEED;
    }
}
