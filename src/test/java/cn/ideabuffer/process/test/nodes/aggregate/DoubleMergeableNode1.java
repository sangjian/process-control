package cn.ideabuffer.process.test.nodes.aggregate;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.nodes.AbstractMergeableNode;

/**
 * @author sangjian.sj
 * @date 2020/03/11
 */
public class DoubleMergeableNode1 extends AbstractMergeableNode<Double> {

    @Override
    protected Double doInvoke(Context context) throws Exception {
        return 12d;
    }
}
