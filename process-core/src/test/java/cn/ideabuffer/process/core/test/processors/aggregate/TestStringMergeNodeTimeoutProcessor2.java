package cn.ideabuffer.process.core.test.processors.aggregate;

import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.context.Context;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sangjian.sj
 * @date 2020/05/09
 */
public class TestStringMergeNodeTimeoutProcessor2 implements Processor<String> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String process(@NotNull Context context) throws Exception {
        logger.info("in TestStringMergeNodeTimeoutProcessor2");
        Thread.sleep(1000);
        return "test2";
    }
}
