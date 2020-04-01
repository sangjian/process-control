package cn.ideabuffer.process.core.test.nodes;

import cn.ideabuffer.process.core.block.Block;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class TestBlockNode2 extends AbstractExecutableNode {

    @NotNull
    @Override
    protected ProcessStatus doExecute(Context context) throws Exception {
        // 获取当前Block
        Block block = context.getBlock();
        Key<Integer> key = Contexts.newKey("k", int.class);
        int k = block.get(key, 0);
        logger.info("before put, k in Block: {}", k);
        // 设置当前Block的变量k
        block.put(key, 200);
        logger.info("after put, k in Block: {}", block.get(key));
        return ProcessStatus.PROCEED;
    }
}
