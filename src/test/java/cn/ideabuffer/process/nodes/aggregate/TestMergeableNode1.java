package cn.ideabuffer.process.nodes.aggregate;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.nodes.AbstractMergeableNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/03/09
 */
public class TestMergeableNode1 extends AbstractMergeableNode<List<String>> {

    private static final Logger logger = LoggerFactory.getLogger(TestMergeableNode1.class);

    @Override
    protected List<String> doInvoke(Context context) {
        logger.info("in TestMergeableNode1");
        List<String> list = new ArrayList<>();
        list.add("test1");
        return list;
    }
}
