package cn.ideabuffer.process.nodes;

import cn.ideabuffer.process.Context;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class TestBreakNode extends AbstractExecutableNode {
    @Override
    protected boolean doExecute(Context context) throws Exception {
        if (context.getBlock().allowBreak()) {
            logger.info("start break, k = " + context.getBlock().get("k"));
            context.getBlock().doBreak();
        }
        return false;
    }
}
