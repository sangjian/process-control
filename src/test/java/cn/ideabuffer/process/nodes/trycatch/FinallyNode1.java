package cn.ideabuffer.process.nodes.trycatch;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.nodes.AbstractExecutableNode;

/**
 * @author sangjian.sj
 * @date 2020/02/28
 */
public class FinallyNode1 extends AbstractExecutableNode {
    @Override
    public boolean doExecute(Context context) throws Exception {
        System.out.println("in FinallyNode1");
        return false;
    }
}
