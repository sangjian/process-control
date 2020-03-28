package cn.ideabuffer.process.test.nodes.aggregate;

import cn.ideabuffer.process.context.Context;
import cn.ideabuffer.process.nodes.AbstractDistributeMergeableNode;

/**
 * @author sangjian.sj
 * @date 2020/03/27
 */
public class TestDistributeMergeNode1 extends AbstractDistributeMergeableNode<Integer, Person> {
    @Override
    protected Integer doInvoke(Context context) throws Exception {
        return 30;
    }

    @Override
    public Person merge(Integer age, Person person) {
        person.setAge(age);
        return person;
    }
}
