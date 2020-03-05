package cn.ideabuffer.process;

import cn.ideabuffer.process.condition.Conditions;
import cn.ideabuffer.process.nodes.DefaultChain;
import cn.ideabuffer.process.nodes.TryCatchFinallyNode;
import cn.ideabuffer.process.nodes.ifs.TestFalseBrance;
import cn.ideabuffer.process.nodes.ifs.TestIfRule;
import cn.ideabuffer.process.nodes.ifs.TestTrueBrance;
import cn.ideabuffer.process.nodes.trycatch.*;
import cn.ideabuffer.process.nodes.whiles.TestWhileNode1;
import cn.ideabuffer.process.nodes.whiles.TestWhileRule;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public class ChainTest {


    @Test
    public void testChainBase() throws Exception {
        //ExecutorService executorService = Executors.newFixedThreadPool(3);
        //Chain chain = new DefaultChain("testChain");
        //Chain c2 = new DefaultChain("testSubChain");
        //c2.addProcessNode(new TestNode1("c-test1"))
        //    .addProcessNode(new TestNode2("c-test2"));
        //Context context = new DefaultContext();
        //context.put("k", 0);
        //chain
        //    .addProcessNode(new TestNode1("test1"))
        //    //.addConditionNode(new TestCondition1("testCondition1")
        //    //                    .trueBranch(new TestNode1("testNode1"))
        //    //                    .falseBranch(new TestNode1("testNode2")));
        //    .addNodeGroup(new NodeGroup("testGroup")
        //        .addNodes(new TestGroupNode1("testGroup1"), new TestGroupNode2("testGroup2"))
        //        .executeOn(executorService))
        //    .addProcessNode(new TestNode2("test2"));
        //chain.execute(context);

    }

    @Test
    public void testIf() throws Exception {
        Chain chain = new DefaultChain("testChain");
        Context context = new DefaultContext();
        context.put("k", 1);
        TestIfRule rule = new TestIfRule();
        chain.addConditionNode(Conditions.newIf().when(rule).then(new TestTrueBrance())
            .otherwise(new TestFalseBrance()));
        chain.execute(context);
    }

    @Test
    public void testWhile() throws Exception {
        Chain chain = new DefaultChain("testChain");
        Context context = new DefaultContext();
        TestWhileRule rule = new TestWhileRule();
        chain.addConditionNode(Conditions.newWhile().when(rule)
                                .then(new TestWhileNode1("testWhileNode1"), new TestWhileNode1("testWhileNode2"), new TestWhileNode1("testWhileNode3")));
        chain.execute(context);
    }

    @Test
    public void testSerial() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Chain chain = new DefaultChain("testChain");
        Context context = new DefaultContext();
        context.put("k", 1);
        //chain.addNodeGroup(new NodeGroup()
        //    .addNodes(new TestGroupNode1("testGroup1"), new TestGroupNode2("testGroup2")));
        long start = System.currentTimeMillis();
        chain.execute(context);
        System.out.println("cost:" + (System.currentTimeMillis() - start) / 1000);
    }

    @Test
    public void testBlock() {
        Chain chain = new DefaultChain("testChain");
        Context context = new DefaultContext();
        context.put("k", 1);

    }

    @Test
    public void testTryCatchFinally() throws Exception {
        Chain chain = new DefaultChain("testChain");
        Context context = new DefaultContext();
        context.put("k", 1);
        chain.addProcessNode(new TryCatchFinallyNode()
            .addTryNode(new TryNode1())
            .addTryNode(new TryNode2())
            .addCatchNode(new CatchNode1())
            .addCatchNode(new CatchNode2())
            .addFinallyNode(new FinallyNode1())
            .addFinallyNode(new FinallyNode2())
            .catchOnException(NullPointerException.class));
        chain.execute(context);
    }

    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {
            if(i < 101) {
                if (i % 2 == 0) {
                    if (i == 2) {
                        break;
                    }
                    System.out.println("after i == 16 break, i = " + i);
                }
                System.out.println("in i < 101");
            }
        }
        System.out.println("outside");
    }

}
