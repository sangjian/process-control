package cn.ideabuffer.process.test.nodes;

import cn.ideabuffer.process.context.Context;
import cn.ideabuffer.process.nodes.AbstractBaseNode;

/**
 * @author sangjian.sj
 * @date 2020/03/25
 */
public class TestBaseNode extends AbstractBaseNode<String> {

    @Override
    protected String doInvoke(Context context) {
        return "TestBaseNode";
    }
}
