package cn.ideabuffer.process.core.test.nodes.ifs;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.nodes.branch.DefaultBranchNode;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class TestFalseBranch extends DefaultBranchNode {

    @NotNull
    @Override
    public ProcessStatus execute(Context context) throws Exception {
        Key<Integer> key = Contexts.newKey("k", int.class);
        logger.info("in false branch, k:{}", context.get(key));
        context.put(key, 11);
        logger.info("in false branch, k:{}", context.get(key));
        super.execute(context);
        return ProcessStatus.proceed();
    }

}
