package cn.ideabuffer.process.core.test.nodes.parallel;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.core.processors.StatusProcessor;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sangjian.sj
 * @date 2020/03/10
 */
public class TestParallelNodeProcessor1 implements StatusProcessor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public ProcessStatus process(@NotNull Context context) throws Exception {
        logger.info("in TestParallelNodeProcessor1");
        Thread.sleep(1000);
        return ProcessStatus.PROCEED;
    }
}
