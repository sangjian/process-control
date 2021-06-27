package cn.ideabuffer.process.core.test.processors.executable;

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
 * @date 2020/04/01
 */
public class TestExceptionProcessor1 implements StatusProcessor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @NotNull
    @Override
    public ProcessStatus process(@NotNull Context context) throws Exception {
        Key<Integer> key = Contexts.newKey("k", int.class);
        logger.info("in TestExceptionProcessor1, k:{}", context.get(key));
        context.put(key, 5);
        throw new RuntimeException("test exception!");
    }
}