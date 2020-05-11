package cn.ideabuffer.process.core.test.nodes;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.core.processors.StatusProcessor;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class TestBreakProcessor implements StatusProcessor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @NotNull
    @Override
    public ProcessStatus process(@NotNull Context context) throws Exception {
        if (context.getBlock().allowBreak()) {
            Key<Integer> key = Contexts.newKey("k", int.class);
            logger.info("start break, k = " + context.getBlock().get(key));
            context.getBlock().doBreak();
        }
        return ProcessStatus.PROCEED;
    }
}
