package cn.ideabuffer.process.nodes.parallel;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.nodes.AbstractExecutableNode;

/**
 * @author sangjian.sj
 * @date 2020/03/10
 */
public class TestParallelNode2 extends AbstractExecutableNode {

    @Override
    protected boolean doExecute(Context context) throws Exception {
        logger.info("in TestParallelNode2");
        Thread.sleep(10000);
        return false;
    }
}
