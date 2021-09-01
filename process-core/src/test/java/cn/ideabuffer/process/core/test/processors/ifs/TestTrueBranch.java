package cn.ideabuffer.process.core.test.processors.ifs;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.branch.DefaultBranchNode;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class TestTrueBranch extends DefaultBranchNode {
    @NotNull
    @Override
    public ProcessStatus execute(@NotNull Context context) throws Exception {
        logger.info("in true branch");
        return super.execute(context);
    }
}
