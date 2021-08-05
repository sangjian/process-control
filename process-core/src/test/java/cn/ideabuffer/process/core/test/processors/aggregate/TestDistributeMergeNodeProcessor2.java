package cn.ideabuffer.process.core.test.processors.aggregate;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.processors.DistributeProcessor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sangjian.sj
 * @date 2020/03/27
 */
public class TestDistributeMergeNodeProcessor2 implements DistributeProcessor<String, Person> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String process(@NotNull Context context) throws Exception {
        logger.info("in TestDistributeMergeNodeProcessor2 doInvoke");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Flash";
    }

    @Override
    public Person merge(String s, @NotNull Person person) {
        logger.info("in TestDistributeMergeNodeProcessor2 merge");
        person.setName(s);
        return person;
    }
}
