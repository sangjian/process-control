package cn.ideabuffer.process.nodes;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.group.AbstractExecutableNodeGroup;

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
