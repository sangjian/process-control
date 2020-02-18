package cn.ideabuffer.process.nodes;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.ExpectableNode;
import cn.ideabuffer.process.PostProcessor;
import cn.ideabuffer.process.AbstractExecutableNode;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public class TestNode2 extends AbstractExecutableNode implements PostProcessor, ExpectableNode<Integer> {

    public TestNode2(String id) {
        super(id);
    }

    @Override
    public boolean enabled(Context context) {
        return true;
    }

    @Override
    public boolean execute(Context context) throws Exception {
        System.out.println("id=" + getId() + ", k = " + context.get("k"));
        Thread.sleep(2000);
        return false;
    }

    @Override
    public boolean postProcess(Context context, Exception e) {
        System.out.println("in " + getId() + " post");
        return false;
    }

    @Override
    public Integer expectation() {
        return 0;
    }

}
