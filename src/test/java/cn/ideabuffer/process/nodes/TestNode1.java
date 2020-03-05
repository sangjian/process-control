package cn.ideabuffer.process.nodes;

import cn.ideabuffer.process.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class TestNode1 extends AbstractExecutableNode {

    private static final Logger logger = LoggerFactory.getLogger(TestNode1.class);

    @Override
    protected boolean doExecute(Context context) throws Exception {
        logger.info("in testNode1");
        return false;
    }
}
