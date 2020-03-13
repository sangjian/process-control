package cn.ideabuffer.process.nodes.ifs;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.nodes.branch.DefaultBranch;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class TestTrueBranch extends DefaultBranch {
    @Override
    public boolean execute(Context context) throws Exception {
        logger.info("in true branch");
        return super.execute(context);
    }
}
