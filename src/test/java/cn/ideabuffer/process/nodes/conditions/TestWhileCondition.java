package cn.ideabuffer.process.nodes.conditions;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.condition.AbstractWhileConditionNode;

/**
 * @author sangjian.sj
 * @date 2020/01/20
 */
public class TestWhileCondition extends AbstractWhileConditionNode {
    public TestWhileCondition(String id) {
        super(id);
    }

    @Override
    public Boolean judge(Context context) {
        int k = context.get("k", 0);
        return k < 10;
    }

    @Override
    public boolean enabled(Context context) {
        return true;
    }
}
