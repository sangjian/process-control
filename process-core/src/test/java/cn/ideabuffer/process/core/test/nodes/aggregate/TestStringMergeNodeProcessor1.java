package cn.ideabuffer.process.core.test.nodes.aggregate;

import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.context.Context;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sangjian.sj
 * @date 2020/05/09
 */
public class TestStringMergeNodeProcessor1 implements Processor<String> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String process(@NotNull Context context) throws Exception {
        logger.info("in TestStringMergeNodeProcessor1");
        return "test1";
    }
}
