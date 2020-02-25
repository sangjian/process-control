package cn.ideabuffer.process.nodes;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.AbstractExecutableNode;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public class TestGroupNode1 extends AbstractExecutableNode {
    public TestGroupNode1(String id) {
        super(id);
    }

    @Override
    public boolean enabled(Context context) {
        return true;
    }

    @Override
    public boolean execute(Context context) throws Exception {
        Thread.sleep(10000);
        System.out.println(Thread.currentThread().getName() + "in testGroupNode1");
        return false;
    }

}
