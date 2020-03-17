package cn.ideabuffer.process.test.nodes.parallel;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.nodes.AbstractExecutableNode;

/**
 * @author sangjian.sj
 * @date 2020/03/10
 */
public class TestParallelNode1 extends AbstractExecutableNode {

    @Override
    protected boolean doExecute(Context context) throws Exception {
        logger.info("in TestParallelNode1");
        Thread.sleep(1000);
        return false;
    }
}
