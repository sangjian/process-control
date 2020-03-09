package cn.ideabuffer.process.nodes;

import cn.ideabuffer.process.Context;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class TestNode2 extends AbstractExecutableNode {

    @Override
    protected boolean doExecute(Context context) throws Exception {
        logger.info("in testNode2");
        Thread.sleep(1000);
        return false;
    }
}
