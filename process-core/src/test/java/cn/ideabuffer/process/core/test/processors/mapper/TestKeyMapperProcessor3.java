package cn.ideabuffer.process.core.test.processors.mapper;

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
 * @date 2020/05/21
 */
public class TestKeyMapperProcessor3 implements StatusProcessor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public ProcessStatus process(@NotNull Context context) throws Exception {
        Key<Integer> key = Contexts.newKey("newK", int.class);
        logger.info("in TestKeyMapperProcessor3, newK:{}", context.get(key));
        //context.put(key, 456);
        Thread.sleep(1000);
        return ProcessStatus.proceed();
    }
}
