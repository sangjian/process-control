package cn.ideabuffer.process.core.test.nodes.parallel;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/03/10
 */
public class TestParallelNode2 extends AbstractExecutableNode {

    @Override
    protected ProcessStatus doExecute(Context context) throws Exception {
        logger.info("in TestParallelNode2");
        Thread.sleep(10000);
        return ProcessStatus.PROCEED;
    }
}
