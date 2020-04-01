package cn.ideabuffer.process.core.test.nodes.executable;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/04/01
 */
public class TestExecutableNode2 extends AbstractExecutableNode {

    @NotNull
    @Override
    protected ProcessStatus doExecute(Context context) throws Exception {
        Key<Integer> key = Contexts.newKey("k", int.class);
        logger.info("in TestExecutableNode2, k:{}", context.get(key));
        Thread.sleep(1000);
        return ProcessStatus.PROCEED;
    }
}