package cn.ideabuffer.process.core.test.nodes.aggregate;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.AbstractMergeableNode;

/**
 * @author sangjian.sj
 * @date 2020/03/11
 */
public class DoubleMergeableNode2 extends AbstractMergeableNode<Double> {

    @Override
    protected Double doInvoke(Context context) throws Exception {
        return 13d;
    }
}
