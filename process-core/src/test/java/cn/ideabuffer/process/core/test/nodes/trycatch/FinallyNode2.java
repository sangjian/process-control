package cn.ideabuffer.process.core.test.nodes.trycatch;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/02/28
 */
public class FinallyNode2 extends AbstractExecutableNode {
    @NotNull
    @Override
    public ProcessStatus doExecute(Context context) throws Exception {
        System.out.println("in FinallyNode2");
        return ProcessStatus.PROCEED;
    }
}