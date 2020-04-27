package cn.ideabuffer.process.core.test.nodes.aggregate;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.AbstractMergeableNode;

/**
 * @author sangjian.sj
 * @date 2020/03/11
 */
public class IntMergeableNode2 extends AbstractMergeableNode<Integer> {

    @Override
    protected Integer doInvoke(Context context) throws Exception {
        return 13;
    }
}