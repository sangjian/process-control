package cn.ideabuffer.process.core.test.handlers;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.wrapper.WrapperHandler;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sangjian.sj
 * @date 2021/06/13
 */
public class TestHandler1 implements WrapperHandler<Integer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestHandler1.class);

    @Override
    public void before(@NotNull Context context) {
        LOGGER.info("in TestHandler1 before");
    }

    @Override
    public void afterReturning(@NotNull Context context, @Nullable Integer result, @NotNull ProcessStatus status) {
        LOGGER.info("in TestHandler1 afterReturning");

    }

    @Override
    public void afterThrowing(@NotNull Context context, @NotNull Throwable t) {
        LOGGER.info("in TestHandler1 afterThrowing");
    }
}
