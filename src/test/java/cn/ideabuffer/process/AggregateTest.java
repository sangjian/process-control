package cn.ideabuffer.process;

import cn.ideabuffer.process.nodes.AggregatableNode;
import cn.ideabuffer.process.nodes.Nodes;
import cn.ideabuffer.process.nodes.aggregate.TestMergeableNode1;
import cn.ideabuffer.process.nodes.aggregate.TestMergeableNode2;
import cn.ideabuffer.process.nodes.merger.ArrayListMerger;
import org.junit.Test;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/03/09
 */
public class AggregateTest {

    @Test
    public void testPostProcessor() throws Exception {
        Chain chain = new DefaultChain();
        Context context = new DefaultContext();
        AggregatableNode<List<String>> node = Nodes.newAggregatableNode();
        node.merge(new TestMergeableNode1(), new TestMergeableNode2()).by(new ArrayListMerger<>())
            .thenApply(((ctx, result) -> {
                System.out.println(result);
                return result.size();
            })).thenApply((ctx, result) -> {
            System.out.println(result);
            return null;
        }).thenAccept((ctx, result) -> System.out.println(result));
        chain.addAggregateNode(node);
        chain.execute(context);
        //Thread.sleep(10000);
    }

}
