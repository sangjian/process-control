package cn.ideabuffer.process.core.test.nodes.aggregate;

import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.context.Context;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/05/09
 */
public class TestListMergeNodeProcessor2 implements Processor<List<String>> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<String> process(@NotNull Context context) throws Exception {
        logger.info("in TestListMergeNodeProcessor2");
        List<String> list = new ArrayList<>();
        list.add("test1");
        return list;
    }
}
