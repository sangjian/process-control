package cn.ideabuffer.process.core.test.nodes;

import cn.ideabuffer.process.core.Lifecycle;
import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.processors.StatusProcessor;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class TestProcessor2 implements Processor<Integer>, Lifecycle {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @NotNull
    @Override
    public Integer process(@NotNull Context context) throws Exception {
        Key<Integer> key = Contexts.newKey("k", int.class);
        logger.info("in TestProcessor2, k:{}", context.get(key));
        return 2;
    }

    @Override
    public void initialize() {
        logger.info("in TestProcessor2, initializing");
    }

    @Override
    public void destroy() {
        logger.info("in TestProcessor2, destroying");
    }
}
