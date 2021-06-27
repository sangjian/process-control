package cn.ideabuffer.process.core.test.processors.transmitter;

import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.context.Context;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/03/10
 */
public class TestTransmittableProcessor implements Processor<String> {

    @Override
    public String process(@NotNull Context context) throws Exception {
        return "hello";
    }
}
