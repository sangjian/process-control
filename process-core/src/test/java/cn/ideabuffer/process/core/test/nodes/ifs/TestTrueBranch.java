package cn.ideabuffer.process.core.test.nodes.ifs;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.branch.DefaultBranchNode;
import cn.ideabuffer.process.core.status.ProcessStatus;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class TestTrueBranch extends DefaultBranchNode {
    @Override
    public ProcessStatus execute(Context context) throws Exception {
        logger.info("in true branch");
        return super.execute(context);
    }
}
