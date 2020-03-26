package cn.ideabuffer.process.test.nodes;

import cn.ideabuffer.process.context.Context;
import cn.ideabuffer.process.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.status.ProcessStatus;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class TestBreakNode extends AbstractExecutableNode {
    @Override
    protected ProcessStatus doExecute(Context context) throws Exception {
        if (context.getBlock().allowBreak()) {
            logger.info("start break, k = " + context.getBlock().get("k"));
            context.getBlock().doBreak();
        }
        return ProcessStatus.PROCEED;
    }
}
