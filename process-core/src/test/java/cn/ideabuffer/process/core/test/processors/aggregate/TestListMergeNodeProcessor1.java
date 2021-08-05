package cn.ideabuffer.process.core.test.processors.aggregate;

import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Key;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/05/09
 */
public class TestListMergeNodeProcessor1 implements Processor<List<String>> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<String> process(@NotNull Context context) throws Exception {
        logger.info("in TestListMergeNodeProcessor1");
        context.get(new Key<>("123", Integer.class));
        List<String> list = new ArrayList<>();
        Thread.sleep(2000);
        list.add("test1");
        return list;
    }
}
