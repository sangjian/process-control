package cn.ideabuffer.process;

import cn.ideabuffer.process.nodes.*;
import cn.ideabuffer.process.nodes.cases.TestCaseNode;
import cn.ideabuffer.process.nodes.cases.TestCaseNode2;
import cn.ideabuffer.process.nodes.cases.TestCaseNode3;
import cn.ideabuffer.process.nodes.cases.TestWhileNode1;
import cn.ideabuffer.process.nodes.conditions.TestSwitchCondition;
import cn.ideabuffer.process.nodes.conditions.TestWhileCondition;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public class ChainTest {


    //@Test
    //public void testChainBase() throws Exception {
    //    ExecutorService executorService = Executors.newFixedThreadPool(3);
    //    Chain chain = new ChainBase("testChain");
    //    Chain c2 = new ChainBase("testSubChain");
    //    c2.addProcessNode(new TestNode1("c-test1"))
    //        .addProcessNode(new TestNode2("c-test2"));
    //    Context context = new DefaultContext();
    //    context.put("k", 0);
    //    chain
    //        .addProcessNode(new TestNode1("test1"))
    //        //.addConditionNode(new TestCondition1("testCondition1")
    //        //                    .addTrueNode(new TestNode1("testNode1"))
    //        //                    .addFalseNode(new TestNode1("testNode2")));
    //        .addNodeGroup(new TestGroup("testGroup")
    //                        .addNode(new TestGroupNode1("testGroup1"))
    //                        .addNode(new TestGroupNode2("testGroup2"))
    //                        //.addNode(c2)
    //                        .executeFrom(executorService))
    //        .addProcessNode(new TestNode2("test2"));
    //    chain.execute(context);
    //
    //}
    //
    //@Test
    //public void testSwitch() throws Exception {
    //    Chain chain = new ChainBase("testChain");
    //    Context context = new DefaultContext();
    //    context.put("k", 1);
    //    chain.addConditionNode(new TestSwitchCondition("testSwitchCondition")
    //                            .switchCase(new TestCaseNode("testCaseNode1"))
    //                            .switchCase(new TestCaseNode2("testCaseNode2"))
    //                            .defaultCase(new TestCaseNode3("testCaseNode3")));
    //    chain.execute(context);
    //}
    //
    //@Test
    //public void testWhile() throws Exception {
    //    Chain chain = new ChainBase("testChain");
    //    Context context = new DefaultContext();
    //    context.put("k", 1);
    //    chain.addConditionNode(new TestWhileCondition("testWhileCondition")
    //                            .addNode(new TestWhileNode1("testWhileNode1"))
    //                            .addNode(new TestWhileNode1("testWhileNode2"))
    //                            .addNode(new TestWhileNode1("testWhileNode3")));
    //    chain.execute(context);
    //}

    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {
            if(i % 2 == 0) {
                switch (i) {
                    case 16:
                        continue;
                }
                System.out.println("after i == 16 break");
            }
        }
        System.out.println("outside");
    }

}
