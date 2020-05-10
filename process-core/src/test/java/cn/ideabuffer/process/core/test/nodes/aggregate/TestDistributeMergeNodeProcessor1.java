package cn.ideabuffer.process.core.test.nodes.aggregate;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.processors.DistributeProcessor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sangjian.sj
 * @date 2020/03/27
 */
public class TestDistributeMergeNodeProcessor1 implements DistributeProcessor<Integer, Person> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Integer process(@NotNull Context context) throws Exception {
        logger.info("in TestDistributeMergeNodeProcessor1 doInvoke");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 30;
    }

    @Override
    public Person merge(Integer age, @NotNull Person person) {
        logger.info("in TestDistributeMergeNodeProcessor1 merge");
        person.setAge(age);
        return person;
    }
}
