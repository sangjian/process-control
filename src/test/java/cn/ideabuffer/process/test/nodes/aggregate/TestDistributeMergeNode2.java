package cn.ideabuffer.process.test.nodes.aggregate;

import cn.ideabuffer.process.context.Context;
import cn.ideabuffer.process.nodes.AbstractDistributeMergeableNode;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/03/27
 */
public class TestDistributeMergeNode2 extends AbstractDistributeMergeableNode<String, Person> {

    @Override
    protected String doInvoke(Context context) throws Exception {
        logger.info("in TestDistributeMergeNode2 doInvoke");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "sangjian";
    }

    @Override
    public Person merge(String s, @NotNull Person person) {
        logger.info("in TestDistributeMergeNode2 merge");
        person.setName(s);
        return person;
    }
}
