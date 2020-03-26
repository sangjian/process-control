package cn.ideabuffer.process.test.nodes;

import cn.ideabuffer.process.context.Context;
import cn.ideabuffer.process.context.Contexts;
import cn.ideabuffer.process.context.Key;
import cn.ideabuffer.process.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.status.ProcessStatus;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class TestNode2 extends AbstractExecutableNode {

    @Override
    protected ProcessStatus doExecute(Context context) throws Exception {
        Key<Integer> key = Contexts.newKey("k", int.class);
        logger.info("in testNode2, k:{}", context.get(key));
        Thread.sleep(1000);
        return ProcessStatus.PROCEED;
    }
}
