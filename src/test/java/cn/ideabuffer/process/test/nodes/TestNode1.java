package cn.ideabuffer.process.test.nodes;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.nodes.AbstractExecutableNode;

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
