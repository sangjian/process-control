package cn.ideabuffer.process.nodes.conditions;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.condition.AbstractSwitchConditionNode;

/**
 * @author sangjian.sj
 * @date 2020/01/20
 */
public class TestSwitchCondition extends AbstractSwitchConditionNode<Integer> {
    public TestSwitchCondition(String id) {
        super(id);
    }

    @Override
    public Integer judge(Context context) {
        return (Integer)context.get("k");
    }

    @Override
    public boolean enabled(Context context) {
        return true;
    }
}
