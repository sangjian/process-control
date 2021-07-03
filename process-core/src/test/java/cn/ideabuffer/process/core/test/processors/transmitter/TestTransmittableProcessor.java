package cn.ideabuffer.process.core.test.processors.transmitter;

import cn.ideabuffer.process.core.Lifecycle;
import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.context.Context;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sangjian.sj
 * @date 2020/03/10
 */
public class TestTransmittableProcessor implements Processor<String>, Lifecycle {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String process(@NotNull Context context) throws Exception {
        return "hello";
    }

    @Override
    public void initialize() {
        logger.info("TestTransmittableProcessor initializing");
    }

    @Override
    public void destroy() {
        logger.info("TestTransmittableProcessor destroying");
    }
}
