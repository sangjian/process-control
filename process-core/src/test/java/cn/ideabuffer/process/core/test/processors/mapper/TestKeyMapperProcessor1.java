package cn.ideabuffer.process.core.test.processors.mapper;

import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.context.Key;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sangjian.sj
 * @date 2020/05/21
 */
public class TestKeyMapperProcessor1 implements Processor<Integer> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Integer process(@NotNull Context context) throws Exception {
        Key<Integer> key = Contexts.newKey("k", int.class);
        logger.info("in TestKeyMapperProcessor1, k:{}", context.get(key));
        Thread.sleep(1000);
        return 2;
    }
}
