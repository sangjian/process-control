package cn.ideabuffer.process.nodes;

import cn.ideabuffer.process.AbstractExecutableNode;
import cn.ideabuffer.process.Context;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public class TestGroupNode2 extends AbstractExecutableNode {
    public TestGroupNode2(String id) {
        super(id);
    }

    @Override
    public boolean enabled(Context context) {
        return true;
    }

    @Override
    public boolean execute(Context context) throws Exception {
        Thread.sleep(1000);
        System.out.println(Thread.currentThread().getName() + "in testGroupNode2");
        return false;
    }

}
