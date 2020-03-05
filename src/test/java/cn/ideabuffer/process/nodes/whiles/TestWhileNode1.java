package cn.ideabuffer.process.nodes.whiles;

import cn.ideabuffer.process.block.Block;
import cn.ideabuffer.process.nodes.AbstractExecutableNode;
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
    public boolean doExecute(Context context) throws Exception {
        Block block = context.getBlock();
        int k = block.get("k", 0);
        block.put("k", ++k);
        if(k == 4) {
            block.doBreak();
        }
        System.out.println(String.format("k:%d", k));
        return false;
    }
}
