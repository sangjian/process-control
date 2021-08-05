package cn.ideabuffer.process.core.test.processors.lifecycle;

import cn.ideabuffer.process.core.Lifecycle;
import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.context.Context;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author sangjian.sj
 * @date 2021/06/27
 */
public class TestLifecycleProcessor1 implements Processor<String>, Lifecycle {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private AtomicInteger initializeCounter = new AtomicInteger();
    private AtomicInteger destroyCounter = new AtomicInteger();

    @Override
    public void initialize() {
        initializeCounter.incrementAndGet();
        logger.info("TestLifecycleProcessor1 initializing");
    }

    @Override
    public void destroy() {
        destroyCounter.incrementAndGet();
        logger.info("TestLifecycleProcessor1 destroying");
    }

    @Nullable
    @Override
    public String process(@NotNull Context context) throws Exception {
        return null;
    }

    public AtomicInteger getInitializeCounter() {
        return initializeCounter;
    }

    public AtomicInteger getDestroyCounter() {
        return destroyCounter;
    }
}
