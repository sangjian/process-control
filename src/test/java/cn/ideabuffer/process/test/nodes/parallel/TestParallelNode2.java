package cn.ideabuffer.process.test.nodes.parallel;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.status.ProcessStatus;

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
