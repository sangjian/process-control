package cn.ideabuffer.process.nodes.cases;

import cn.ideabuffer.process.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.ExpectableNode;

/**
 * @author sangjian.sj
 * @date 2020/01/19
 */
public class TestCaseNode3 extends AbstractExecutableNode implements ExpectableNode<Integer> {
    public TestCaseNode3(String id) {
        super(id);
    }

    @Override
    public Integer expectation() {
        return 3;
    }

    @Override
    public boolean execute(Context context) throws Exception {
        System.out.println("id = " + getId() + "k = " + context.get("k"));
        return false;
    }

}
