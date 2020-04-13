package cn.ideabuffer.process.test.spring.nodes;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.AbstractBaseNode;
import cn.ideabuffer.process.core.status.ProcessStatus;
import cn.ideabuffer.process.test.spring.key.Keys;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sangjian.sj
 * @date 2020/04/09
 */
public class TestBaseNode extends AbstractBaseNode<String> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void onInitialize() {
        logger.info("onInitialize");
    }

    @Override
    protected void onDestroy() {
        logger.info("onDestroy");
    }

    @Override
    protected String doInvoke(Context context, @NotNull ProcessStatus status) {
        logger.info("in TestBaseNode");
        return context.get(Keys.testKey1);
    }
}
