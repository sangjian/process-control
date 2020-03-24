package cn.ideabuffer.process.test.nodes.ifs;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.nodes.branch.DefaultBranchNode;
import cn.ideabuffer.process.status.ProcessStatus;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class TestFalseBranch extends DefaultBranchNode {

    @Override
    public ProcessStatus execute(Context context) throws Exception {
        logger.info("in false branch, k:{}", context.get("k"));
        context.put("k",11);
        logger.info("in false branch, k:{}", context.get("k"));
        super.execute(context);
        return ProcessStatus.PROCEED;
    }

}
