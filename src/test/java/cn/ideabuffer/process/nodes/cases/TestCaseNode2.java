package cn.ideabuffer.process.nodes.cases;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.ExpectableNode;
import cn.ideabuffer.process.AbstractExecutableNode;

/**
 * @author sangjian.sj
 * @date 2020/01/19
 */
public class TestCaseNode2 extends AbstractExecutableNode implements ExpectableNode<Integer> {

    public TestCaseNode2(String id) {
        super(id);
    }

    @Override
    public Integer expectation() {
        return 2;
    }

    @Override
    public boolean execute(Context context) throws Exception {
        System.out.println("k = " + context.get("k"));
        //if(context.breakable()) {
        //    context.doBreak();
        //}
        return false;
    }

}
