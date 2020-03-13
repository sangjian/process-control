package cn.ideabuffer.process.nodes.ifs;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.nodes.branch.DefaultBranch;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class TestFalseBranch extends DefaultBranch {

    @Override
    public boolean execute(Context context) throws Exception {
        logger.info("in false branch, k:{}", context.get("k"));
        context.put("k",11);
        logger.info("in false branch, k:{}", context.get("k"));
        super.execute(context);
        return false;
    }

}
