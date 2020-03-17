package cn.ideabuffer.process.test.nodes.aggregate;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.nodes.AbstractMergeableNode;

/**
 * @author sangjian.sj
 * @date 2020/03/11
 */
public class IntArrayMergeableNode1 extends AbstractMergeableNode<int[]> {

    @Override
    protected int[] doInvoke(Context context) throws Exception {
        return new int[] {1, 2, 6};
    }
}
