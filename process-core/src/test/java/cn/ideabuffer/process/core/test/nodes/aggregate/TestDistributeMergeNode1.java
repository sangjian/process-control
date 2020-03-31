package cn.ideabuffer.process.core.test.nodes.aggregate;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.AbstractDistributeMergeableNode;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/03/27
 */
public class TestDistributeMergeNode1 extends AbstractDistributeMergeableNode<Integer, Person> {
    @Override
    protected Integer doInvoke(Context context) throws Exception {
        logger.info("in TestDistributeMergeNode1 doInvoke");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 30;
    }

    @Override
    public long getTimeout() {
        return 500;
    }

    @Override
    public Person merge(Integer age, @NotNull Person person) {
        logger.info("in TestDistributeMergeNode1 merge");
        person.setAge(age);
        return person;
    }
}
