package cn.ideabuffer.process.core.test.nodes.trycatch;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.core.processors.StatusProcessor;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sangjian.sj
 * @date 2020/02/28
 */
public class FinallyNodeProcessor1 implements StatusProcessor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public ProcessStatus process(@NotNull Context context) throws Exception {
        System.out.println("in FinallyNodeProcessor1");
        return ProcessStatus.PROCEED;
    }
}
