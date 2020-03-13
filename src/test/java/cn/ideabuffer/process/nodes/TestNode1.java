package cn.ideabuffer.process.nodes;

import cn.ideabuffer.process.Context;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class TestNode1 extends AbstractExecutableNode {

    @Override
    protected boolean doExecute(Context context) throws Exception {
        logger.info("in testNode1, k:{}", context.get("k"));
        Thread.sleep(1000);
        return false;
    }
}
