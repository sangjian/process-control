package cn.ideabuffer.process.nodes.cases;

import cn.ideabuffer.process.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.ExpectableNode;

/**
 * @author sangjian.sj
 * @date 2020/01/19
 */
public class TestCaseNode extends AbstractExecutableNode implements ExpectableNode<Integer> {
    public TestCaseNode(String id) {
        super(id);
    }

    @Override
    public Integer expectation() {
        return 1;
    }

    @Override
    public boolean doExecute(Context context) throws Exception {
        System.out.println("id = " + getId() + "k = " + context.get("k"));
        //if(context.breakable()) {
        //    context.doBreak();
        //}
        return false;
    }

}
