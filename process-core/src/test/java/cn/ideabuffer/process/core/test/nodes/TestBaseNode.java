package cn.ideabuffer.process.core.test.nodes;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.AbstractBaseNode;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/03/25
 */
public class TestBaseNode extends AbstractBaseNode<String> {

    @Override
    protected String doInvoke(Context context, @NotNull ProcessStatus status) {
        return "TestBaseNode";
    }
}
