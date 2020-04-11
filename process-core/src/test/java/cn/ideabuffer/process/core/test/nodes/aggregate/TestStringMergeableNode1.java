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
public class TestStringMergeableNode1 extends AbstractMergeableNode<String> {

    private static final Logger logger = LoggerFactory.getLogger(TestStringMergeableNode1.class);

    @Override
    protected String doInvoke(Context context) {
        logger.info("in TestStringMergeableNode1");
        return "test1";
    }
}
