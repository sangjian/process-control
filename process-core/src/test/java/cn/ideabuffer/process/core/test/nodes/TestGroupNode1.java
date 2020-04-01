package cn.ideabuffer.process.core.test.nodes;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public class TestGroupNode1 extends AbstractExecutableNode {

    @Override
    public boolean enabled() {
        return true;
    }

    @NotNull
    @Override
    public ProcessStatus doExecute(Context context) throws Exception {
        Thread.sleep(10000);
        System.out.println(Thread.currentThread().getName() + "in testGroupNode1");
        return ProcessStatus.PROCEED;
    }

}
