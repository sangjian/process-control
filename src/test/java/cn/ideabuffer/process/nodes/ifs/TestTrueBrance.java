package cn.ideabuffer.process.nodes.ifs;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.branch.DefaultBranch;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class TestTrueBrance extends DefaultBranch {
    @Override
    public boolean execute(Context context) throws Exception {
        System.out.println("in true branch");
        return super.execute(context);
    }
}
