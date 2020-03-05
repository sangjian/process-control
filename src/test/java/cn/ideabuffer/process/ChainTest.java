package cn.ideabuffer.process;

import cn.ideabuffer.process.branch.Branches;
import cn.ideabuffer.process.condition.Conditions;
import cn.ideabuffer.process.nodes.*;
import cn.ideabuffer.process.nodes.ifs.TestFalseBrance;
import cn.ideabuffer.process.nodes.ifs.TestIfRule;
import cn.ideabuffer.process.nodes.ifs.TestTrueBrance;
import cn.ideabuffer.process.nodes.trycatch.*;
import cn.ideabuffer.process.nodes.whiles.TestWhileNode1;
import cn.ideabuffer.process.nodes.whiles.TestWhileNode2;
import cn.ideabuffer.process.nodes.whiles.TestWhileNode3;
import cn.ideabuffer.process.nodes.whiles.TestWhileRule;
import cn.ideabuffer.process.rule.Rule;
import cn.ideabuffer.process.rule.Rules;
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
        Chain chain = new DefaultChain("testChain");
        //Chain c2 = new DefaultChain("testSubChain");
        //c2.addProcessNode(new TestNode1())
        //.addProcessNode(new TestNode2());
        Context context = new DefaultContext();
        context.put("k", 0);
        chain
            .addProcessNode(new TestNode1())
            .addProcessNode(new TestNode2());
        chain.execute(context);

    }

    @Test
    public void testBranch() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Chain chain = new DefaultChain("testChain");
        Context context = new DefaultContext();
        context.put("k", 0);
        chain
            .addProcessNode(Branches.newDefaultBranch(new TestNode1(), new TestNode2()).parallel(executorService));
        chain.execute(context);
        Thread.sleep(10000);
    }

    @Test
    public void testIf() throws Exception {
        Chain chain = new DefaultChain("testChain");
        Context context = new DefaultContext();
        context.put("k", 1);
        TestIfRule rule = new TestIfRule();
        chain.addIf(Conditions.newIf().when(rule).then(new TestTrueBrance())
            .otherwise(new TestFalseBrance()));
        chain.execute(context);
    }

    @Test
    public void testWhile() throws Exception {
        Chain chain = new DefaultChain("testChain");
        Context context = new DefaultContext();
        TestWhileRule rule = new TestWhileRule();
        chain.addWhile(Conditions.newWhile().when(rule)
            .then(new TestWhileNode1(), new TestWhileNode2(),
                new TestWhileNode3())
            .parallel());
        chain.execute(context);
        Thread.sleep(10000);
    }

    @Test
    public void testNesting() throws Exception {
        Chain chain = new DefaultChain("testChain");
        Context context = new DefaultContext();
        TestWhileRule rule = new TestWhileRule();
        chain.addWhile(Conditions.newWhile().when((ctx) -> {
            System.out.println("in while --");
            return true;
        }).then(
            Conditions.newWhile().when(rule)
            .then(
                new TestWhileNode1(),
                new TestWhileNode2(),
                Conditions.newIf().when(rule).then(
                    Conditions.newIf().when((ctx) -> true).then(new TestBreakNode()).end(),
                    new TestNode1())
                    .end(),
                new TestWhileNode3())));
        chain.execute(context);
        Thread.sleep(10000);
    }

    @Test
    public void testAndRule() throws Exception {
        Chain chain = new DefaultChain("testChain");
        Context context = new DefaultContext();
        chain.addIf(Conditions.newIf().when(Rules.and((ctx) -> true, (ctx) -> false)).then(new TestNode1()).otherwise(new TestNode2()));
        chain.execute(context);
    }

    @Test
    public void testOrRule() throws Exception {
        Chain chain = new DefaultChain("testChain");
        Context context = new DefaultContext();
        chain.addIf(Conditions.newIf().when(Rules.or((ctx) -> true, (ctx) -> false)).then(new TestNode1()).otherwise(new TestNode2()));
        chain.execute(context);
    }

    @Test
    public void testNotRule() throws Exception {
        Chain chain = new DefaultChain("testChain");
        Context context = new DefaultContext();
        chain.addIf(Conditions.newIf().when(Rules.not((ctx) -> true)).then(new TestNode1()).otherwise(new TestNode2()));
        chain.execute(context);
    }

    @Test
    public void testSerial() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Chain chain = new DefaultChain("testChain");
        Context context = new DefaultContext();
        context.put("k", 1);
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
