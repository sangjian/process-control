package cn.ideabuffer.process.core.test.nodes;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.core.processors.StatusProcessor;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public class TestGroupNode2 implements StatusProcessor {

    @Override
    public ProcessStatus process(@NotNull Context context) throws Exception {
        Thread.sleep(3000);
        System.out.println(Thread.currentThread().getName() + "in testGroupNode2");
        return ProcessStatus.PROCEED;
    }

}
