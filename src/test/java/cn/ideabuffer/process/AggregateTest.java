package cn.ideabuffer.process;

import cn.ideabuffer.process.nodes.aggregate.DefaultAggregatableNode;
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
        DefaultAggregatableNode<List<String>> node = new DefaultAggregatableNode<>();
        node.merge(new TestMergeableNode1(), new TestMergeableNode2()).by(new ArrayListMerger<>())
            .thenApply((result -> {
                System.out.println(result);
                return result.size();
            })).thenApply(result -> {
                System.out.println(result);
                return null;
            }).thenAccept(System.out::println);
        chain.addAggregateNode(node);
        chain.execute(context);
        //Thread.sleep(10000);
    }

}
