package cn.ideabuffer.process.test.nodes.ifs;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.nodes.branch.DefaultBranchNode;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class TestTrueBranch extends DefaultBranchNode {
    @Override
    public boolean execute(Context context) throws Exception {
        logger.info("in true branch");
        return super.execute(context);
    }
}
