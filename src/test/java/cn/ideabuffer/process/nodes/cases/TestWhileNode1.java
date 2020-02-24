package cn.ideabuffer.process.nodes.cases;

import cn.ideabuffer.process.AbstractExecutableNode;
import cn.ideabuffer.process.Context;

/**
 * @author sangjian.sj
 * @date 2020/01/20
 */
public class TestWhileNode1 extends AbstractExecutableNode {
    public TestWhileNode1(String id) {
        super(id);
    }

    @Override
    public boolean execute(Context context) throws Exception {
        int k = context.get("k", 0);
        context.put("k", ++k);
        if(k == 4) {
            context.getBlock().doContinue();
        }
        System.out.println(String.format("k:%d", k));
        return false;
    }
}
