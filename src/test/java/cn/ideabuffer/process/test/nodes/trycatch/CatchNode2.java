package cn.ideabuffer.process.test.nodes.trycatch;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.nodes.AbstractExecutableNode;

/**
 * @author sangjian.sj
 * @date 2020/02/28
 */
public class CatchNode2 extends AbstractExecutableNode {

    @Override
    public boolean doExecute(Context context) throws Exception {
        System.out.println("in CatchNode2");
        return false;
    }
}