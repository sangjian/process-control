package cn.ideabuffer.process.core.test.nodes.aggregate;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.AbstractMergeableNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/03/09
 */
public class TestStringMergeableNode2 extends AbstractMergeableNode<String> {

    private static final Logger logger = LoggerFactory.getLogger(TestStringMergeableNode2.class);

    @Override
    protected String doInvoke(Context context) {
        logger.info("in TestListMergeableNode2");
        return "test2";
    }
}
