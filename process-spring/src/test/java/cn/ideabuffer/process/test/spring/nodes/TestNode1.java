package cn.ideabuffer.process.test.spring.nodes;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/04/09
 */
public class TestNode1 extends AbstractExecutableNode {
    @Override
    protected void onInitialize() {
        logger.info("onInitialize");
    }

    @Override
    protected void onDestroy() {
        logger.info("onDestroy");
    }

    @Override
    protected @NotNull ProcessStatus doExecute(Context context) throws Exception {
        logger.info("in TestProcessor1");
        return ProcessStatus.PROCEED;
    }
}
