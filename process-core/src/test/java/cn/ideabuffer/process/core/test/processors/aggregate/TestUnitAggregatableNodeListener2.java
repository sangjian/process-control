package cn.ideabuffer.process.core.test.processors.aggregate;

import cn.ideabuffer.process.core.ProcessListener;
import cn.ideabuffer.process.core.context.Context;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/05/06
 */
public class TestUnitAggregatableNodeListener2 implements ProcessListener<List<String>> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onComplete(@NotNull Context context, List<String> result) {
        logger.info("onComplete, result:{}", result);
    }

    @Override
    public void onFailure(@NotNull Context context, Throwable t) {
        logger.info("onFailure, t:{}", t);
    }
}
