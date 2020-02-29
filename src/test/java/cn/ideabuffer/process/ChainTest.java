package cn.ideabuffer.process;

import cn.ideabuffer.process.executor.ExecuteStrategies;
import cn.ideabuffer.process.nodes.*;
import cn.ideabuffer.process.nodes.cases.TestCaseNode;
import cn.ideabuffer.process.nodes.cases.TestCaseNode2;
import cn.ideabuffer.process.nodes.cases.TestCaseNode3;
import cn.ideabuffer.process.nodes.cases.TestWhileNode1;
import cn.ideabuffer.process.nodes.conditions.TestSwitchCondition;
import cn.ideabuffer.process.nodes.conditions.TestWhileCondition;
import cn.ideabuffer.process.nodes.trycatch.*;
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
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Chain chain = new ChainBase("testChain");
        Chain c2 = new ChainBase("testSubChain");
        c2.addProcessNode(new TestNode1("c-test1"))
            .addProcessNode(new TestNode2("c-test2"));
        Context context = new DefaultContext();
        context.put("k", 0);
        chain
            .addProcessNode(new TestNode1("test1"))
            //.addConditionNode(new TestCondition1("testCondition1")
            //                    .addTrueNode(new TestNode1("testNode1"))
            //                    .addFalseNode(new TestNode1("testNode2")));
            .addNodeGroup(new TestGroup("testGroup")
                            .addNode(new TestGroupNode1("testGroup1"))
                            .addNode(new TestGroupNode2("testGroup2"))
                            //.addNode(c2)
                            .executeOn(executorService)
                        )
            .addProcessNode(new TestNode2("test2"));
        chain.execute(context);

    }

    @Test
    public void testSwitch() throws Exception {
        Chain chain = new ChainBase("testChain");
        Context context = new DefaultContext();
        context.put("k", 1);
        chain.addProcessNode(new TestSwitchCondition("testSwitchCondition")
                                .switchCase(new TestCaseNode("testCaseNode1"))
                                .switchCase(new TestCaseNode2("testCaseNode2"))
                                .defaultCase(new TestCaseNode3("testCaseNode3")));
        chain.execute(context);
    }

    @Test
    public void testWhile() throws Exception {
        Chain chain = new ChainBase("testChain");
        Context context = new DefaultContext();
        context.put("k", 1);
        chain.addConditionNode(new TestWhileCondition("testWhileCondition")
                                .addNode(new TestWhileNode1("testWhileNode1"))
                                .addNode(new TestWhileNode1("testWhileNode2"))
                                .addNode(new TestWhileNode1("testWhileNode3")));
        chain.execute(context);
    }

    @Test
    public void testSerial() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Chain chain = new ChainBase("testChain");
        Context context = new DefaultContext();
        context.put("k", 1);
        chain.addNodeGroup(new TestGroup("testGroup")
            .addNode(new TestGroupNode1("testGroup1"))
            .addNode(new TestGroupNode2("testGroup2"))
            .executeOn(executorService, ExecuteStrategies.SERIAL));
        chain.execute(context);
    }

    @Test
    public void testBlock() {
        Chain chain = new ChainBase("testChain");
        Context context = new DefaultContext();
        context.put("k", 1);

    }

    @Test
    public void testTryCatchFinally() throws Exception {
        Chain chain = new ChainBase("testChain");
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
