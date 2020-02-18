package cn.ideabuffer.process.nodes;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.AbstractExecutableNode;
import cn.ideabuffer.process.ExecutableNodeGroupBase;

/**
 * @author sangjian.sj
 * @date 2020/01/19
 */
public class TestGroup extends ExecutableNodeGroupBase {

    public TestGroup(String id) {
        super(id);
    }

    public TestGroup(String id, AbstractExecutableNode... nodes) {
        super(id, nodes);
    }

    @Override
    public boolean enabled(Context context) {
        return true;
    }
}
