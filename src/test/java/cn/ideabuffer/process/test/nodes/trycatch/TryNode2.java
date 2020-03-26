package cn.ideabuffer.process.test.nodes.trycatch;

import cn.ideabuffer.process.context.Context;
import cn.ideabuffer.process.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.status.ProcessStatus;

/**
 * @author sangjian.sj
 * @date 2020/02/28
 */
public class TryNode2 extends AbstractExecutableNode {

    @Override
    public ProcessStatus doExecute(Context context) throws Exception {
        System.out.println("in TryNode2");

        throw new NullPointerException();
    }
}
