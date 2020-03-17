package cn.ideabuffer.process.test.nodes;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.nodes.AbstractExecutableNode;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class TestNode2 extends AbstractExecutableNode {

    @Override
    protected boolean doExecute(Context context) throws Exception {
        logger.info("in testNode2, k:{}", context.get("k"));
        Thread.sleep(1000);
        return false;
    }
}
