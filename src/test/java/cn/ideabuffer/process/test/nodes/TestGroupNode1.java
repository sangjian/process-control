package cn.ideabuffer.process.test.nodes;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.status.ProcessStatus;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public class TestGroupNode1 extends AbstractExecutableNode {

    @Override
    public boolean enabled() {
        return true;
    }

    @Override
    public ProcessStatus doExecute(Context context) throws Exception {
        Thread.sleep(10000);
        System.out.println(Thread.currentThread().getName() + "in testGroupNode1");
        return ProcessStatus.PROCEED;
    }

}
