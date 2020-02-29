package cn.ideabuffer.process.nodes.conditions;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.condition.AbstractIfConditionNode;

/**
 * @author sangjian.sj
 * @date 2020/01/19
 */
public class TestCondition1 extends AbstractIfConditionNode<Integer> {

    public TestCondition1(String id) {
        super(id);
    }

    @Override
    public boolean enabled(Context context) {
        return true;
    }

    @Override
    public Boolean judge(Context context) {
        int i = context.get("k", 0);
        return i > 0;
    }
}
