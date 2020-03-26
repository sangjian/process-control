package cn.ideabuffer.process.test.nodes.ifs;

import cn.ideabuffer.process.context.Context;
import cn.ideabuffer.process.context.Contexts;
import cn.ideabuffer.process.context.ContextKey;
import cn.ideabuffer.process.nodes.branch.DefaultBranchNode;
import cn.ideabuffer.process.status.ProcessStatus;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class TestFalseBranch extends DefaultBranchNode {

    @Override
    public ProcessStatus execute(Context context) throws Exception {
        ContextKey<Integer> key = Contexts.newKey("k", int.class);
        logger.info("in false branch, k:{}", context.get(key));
        context.put(key,11);
        logger.info("in false branch, k:{}", context.get(key));
        super.execute(context);
        return ProcessStatus.PROCEED;
    }

}
