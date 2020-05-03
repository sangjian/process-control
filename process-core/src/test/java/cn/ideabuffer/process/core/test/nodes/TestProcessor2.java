package cn.ideabuffer.process.core.test.nodes;

import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class TestProcessor2 implements Processor<ProcessStatus> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public ProcessStatus process(@NotNull Context context) throws Exception {
        Key<Integer> key = Contexts.newKey("k", int.class);
        logger.info("in testNode2, k:{}", context.get(key));
        Thread.sleep(1000);
        return ProcessStatus.PROCEED;
    }
}