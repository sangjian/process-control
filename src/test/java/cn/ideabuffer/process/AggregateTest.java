package cn.ideabuffer.process;

import cn.ideabuffer.process.nodes.AggregatableNode;
import cn.ideabuffer.process.nodes.Nodes;
import cn.ideabuffer.process.nodes.aggregate.*;
import cn.ideabuffer.process.nodes.merger.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sangjian.sj
 * @date 2020/03/09
 */
public class AggregateTest {

    @Test
    public void testAggregateList() throws Exception {
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

    @Test
    public void testIntSum() throws Exception {
        Chain chain = new DefaultChain();
        Context context = new DefaultContext();
        AggregatableNode<Integer> node = Nodes.newAggregatableNode();
        node.merge(new IntMergeableNode1(), new IntMergeableNode2()).by(new IntSumMerger())
            .thenApply(((ctx, result) -> {
                System.out.println(result);
                return result;
            }));
        chain.addAggregateNode(node);
        chain.execute(context);
        //Thread.sleep(10000);
    }

    @Test
    public void testIntAvg() throws Exception {
        Chain chain = new DefaultChain();
        Context context = new DefaultContext();
        AggregatableNode<Integer> node = Nodes.newAggregatableNode();
        node.merge(new IntMergeableNode1(), new IntMergeableNode2()).by(new IntAvgMerger())
            .thenApply(((ctx, result) -> {
                System.out.println(result);
                return result;
            }));
        chain.addAggregateNode(node);
        chain.execute(context);
        //Thread.sleep(10000);
    }

    @Test
    public void testDoubleSum() throws Exception {
        Chain chain = new DefaultChain();
        Context context = new DefaultContext();
        AggregatableNode<Double> node = Nodes.newAggregatableNode();
        node.merge(new DoubleMergeableNode1(), new DoubleMergeableNode2()).by(new DoubleSumMerger())
            .thenApply(((ctx, result) -> {
                System.out.println(result);
                return result;
            }));
        chain.addAggregateNode(node);
        chain.execute(context);
        //Thread.sleep(10000);
    }

    @Test
    public void testDoubleAvg() throws Exception {
        Chain chain = new DefaultChain();
        Context context = new DefaultContext();
        AggregatableNode<Double> node = Nodes.newAggregatableNode();
        node.merge(new DoubleMergeableNode1(), new DoubleMergeableNode2()).by(new DoubleAvgMerger())
            .thenApply(((ctx, result) -> {
                System.out.println(result);
                return result;
            }));
        chain.addAggregateNode(node);
        chain.execute(context);
        //Thread.sleep(10000);
    }

    @Test
    public void testIntArray() throws Exception {
        Chain chain = new DefaultChain();
        Context context = new DefaultContext();
        AggregatableNode<int[]> node = Nodes.newAggregatableNode();
        node.merge(new IntArrayMergeableNode1(), new IntArrayMergeableNode2()).by(new IntArrayMerger())
            .thenApply(((ctx, result) -> {
                Arrays.stream(result).forEach(System.out::println);
                return result;
            }));
        chain.addAggregateNode(node);
        chain.execute(context);
        //Thread.sleep(10000);
    }

}
