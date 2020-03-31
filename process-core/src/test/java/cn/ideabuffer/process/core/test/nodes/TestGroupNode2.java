package cn.ideabuffer.process.core.test.nodes;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.core.status.ProcessStatus;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public class TestGroupNode2 extends AbstractExecutableNode {

    @Override
    public boolean enabled() {
        return true;
    }

    @Override
    public ProcessStatus doExecute(Context context) throws Exception {
        Thread.sleep(3000);
        System.out.println(Thread.currentThread().getName() + "in testGroupNode2");
        return ProcessStatus.PROCEED;
    }

}
