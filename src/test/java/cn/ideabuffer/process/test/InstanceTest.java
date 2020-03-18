package cn.ideabuffer.process.test;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.DefaultContext;
import cn.ideabuffer.process.DefaultProcessInstance;
import cn.ideabuffer.process.ProcessInstance;
import cn.ideabuffer.process.nodes.Nodes;
import cn.ideabuffer.process.nodes.branch.BranchNode;
import cn.ideabuffer.process.nodes.branch.Branches;
import cn.ideabuffer.process.test.nodes.*;
import cn.ideabuffer.process.rule.Rules;
import cn.ideabuffer.process.test.nodes.TestNode1;
import cn.ideabuffer.process.test.nodes.TestNode2;
import cn.ideabuffer.process.test.nodes.ifs.*;
import cn.ideabuffer.process.test.nodes.whiles.*;
import cn.ideabuffer.process.test.nodes.trycatch.*;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public class InstanceTest {

    @Test
    public void testInstance() throws Exception {
        ProcessInstance instance = new DefaultProcessInstance();
        Context context = new DefaultContext();
        context.put("k", 0);
        instance
            .addProcessNode(new TestNode1())
            .addProcessNode(new TestNode2());
        instance.execute(context);
    }

    @Test
    public void testBranch() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        ProcessInstance instance = new DefaultProcessInstance();
        Context context = new DefaultContext();
        context.put("k", 0);
        instance
            .addBranchNode(Branches.newBranch(new TestNode1(), new TestNode2()).parallel(executorService));
        instance.execute(context);
        Thread.sleep(10000);
    }

    @Test
    public void testIf() throws Exception {
        ProcessInstance instance = new DefaultProcessInstance();
        Context context = new DefaultContext();
        context.put("k", 1);
        BranchNode trueBranch = Branches.newBranch(new TestNode1());
        BranchNode falseBranch = Branches.newBranch(new TestNode1());
        instance.addIf(Nodes.newIf(ctx -> ctx.get("k", 0) < 5).then(trueBranch)
            .otherwise(falseBranch));
        instance.execute(context);
    }

    @Test
    public void testWhile() throws Exception {
        ProcessInstance instance = new DefaultProcessInstance();
        Context context = new DefaultContext();
        TestWhileRule rule = new TestWhileRule();
        instance.addWhile(Nodes.newWhile(rule)
            .then(new TestWhileNode1(), new TestWhileNode2(),
                new TestWhileNode3()));
        instance.execute(context);
    }

    @Test
    public void testNesting() throws Exception {
        ProcessInstance instance = new DefaultProcessInstance();
        Context context = new DefaultContext();
        TestWhileRule rule = new TestWhileRule();
        instance.addWhile(Nodes.newWhile((ctx) -> {
            System.out.println("in while --");
            return true;
        }).then(
            Nodes.newWhile(rule)
                .then(
                    new TestWhileNode1(),
                    new TestWhileNode2(),
                    Nodes.newIf(rule).then(
                        Nodes.newIf((ctx) -> true).then(new TestBreakNode()).end(),
                        new TestNode1())
                        .end(),
                    new TestWhileNode3())));
        instance.execute(context);
        Thread.sleep(10000);
    }

    @Test
    public void testAndRule() throws Exception {
        ProcessInstance instance = new DefaultProcessInstance();
        Context context = new DefaultContext();
        instance.addIf(Nodes.newIf(Rules.and((ctx) -> true, (ctx) -> false)).then(new TestNode1())
            .otherwise(new TestNode2()));
        instance.execute(context);
    }

    @Test
    public void testOrRule() throws Exception {
        ProcessInstance instance = new DefaultProcessInstance();
        Context context = new DefaultContext();
        instance.addIf(Nodes.newIf(Rules.or((ctx) -> true, (ctx) -> false)).then(new TestNode1())
            .otherwise(new TestNode2()));
        instance.execute(context);
    }

    @Test
    public void testNotRule() throws Exception {
        ProcessInstance instance = new DefaultProcessInstance();
        Context context = new DefaultContext();
        instance.addIf(Nodes.newIf(Rules.not((ctx) -> true)).then(new TestNode1()).otherwise(new TestNode2()));
        instance.execute(context);
    }

    @Test
    public void testSerial() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        ProcessInstance instance = new DefaultProcessInstance();
        Context context = new DefaultContext();
        context.put("k", 1);
        long start = System.currentTimeMillis();
        instance.execute(context);
        System.out.println("cost:" + (System.currentTimeMillis() - start) / 1000);
    }

    @Test
    public void testBlock() {
        ProcessInstance instance = new DefaultProcessInstance();
        Context context = new DefaultContext();
        context.put("k", 1);

    }

    @Test
    public void testTryCatchFinally() throws Exception {
        ProcessInstance instance = new DefaultProcessInstance();
        Context context = new DefaultContext();
        context.put("k", 1);
        instance.addProcessNode(Nodes.newTry(new TryNode1(), new TryNode2())
            .catchOn(Exception.class, new CatchNode1(), new CatchNode2())
            .doFinally(new FinallyNode1(), new FinallyNode2()));
        instance.execute(context);
    }

    @Test
    public void testCompletableFuture() throws InterruptedException {
        CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("haha");
            return true;
        });
        Thread.sleep(5000);
        future.whenComplete((r, e) -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            System.out.println(r);
        });
    }

    @Test
    public void testSubChain() throws Exception {
        ProcessInstance subInstance = new DefaultProcessInstance();
        Context context = new DefaultContext();
        context.put("k", 1);
        TestIfRule rule = new TestIfRule();
        subInstance.addIf(Nodes.newIf(rule).then(new TestTrueBranch())
            .otherwise(new TestFalseBranch()));

        ProcessInstance mainInstance = new DefaultProcessInstance();
        mainInstance.addProcessNode(new TestNode1()).addProcessNode(subInstance).addProcessNode(new TestNode2());

        mainInstance.execute(context);
    }
}
