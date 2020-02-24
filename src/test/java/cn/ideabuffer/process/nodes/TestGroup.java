package cn.ideabuffer.process.nodes;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.AbstractExecutableNode;
import cn.ideabuffer.process.AbstractExecutableNodeGroup;
import cn.ideabuffer.process.ExecutableNode;

/**
 * @author sangjian.sj
 * @date 2020/01/19
 */
public class TestGroup extends AbstractExecutableNodeGroup {

    public TestGroup(String id) {
        super(id);
    }

    @Override
    public boolean enabled(Context context) {
        return true;
    }
}
