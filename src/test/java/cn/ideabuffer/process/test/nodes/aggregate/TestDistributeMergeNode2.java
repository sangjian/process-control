package cn.ideabuffer.process.test.nodes.aggregate;

import cn.ideabuffer.process.context.Context;
import cn.ideabuffer.process.nodes.AbstractDistributeMergeableNode;

/**
 * @author sangjian.sj
 * @date 2020/03/27
 */
public class TestDistributeMergeNode2 extends AbstractDistributeMergeableNode<String, Person> {

    @Override
    protected String doInvoke(Context context) throws Exception {
        return "sangjian";
    }

    @Override
    public Person merge(String s, Person person) {
        person.setName(s);
        return person;
    }
}
