package cn.ideabuffer.process.core.test;

import cn.ideabuffer.process.core.DefaultProcessDefinition;
import cn.ideabuffer.process.core.DefaultProcessInstance;
import cn.ideabuffer.process.core.ProcessDefinition;
import cn.ideabuffer.process.core.ProcessInstance;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.test.nodes.TestBaseNode;
import cn.ideabuffer.process.core.test.nodes.TestBreakNode;
import cn.ideabuffer.process.core.test.nodes.TestNode1;
import cn.ideabuffer.process.core.nodes.Nodes;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.nodes.branch.Branches;
import cn.ideabuffer.process.core.rule.Rules;
import cn.ideabuffer.process.core.test.nodes.TestNode2;
import cn.ideabuffer.process.core.test.nodes.ifs.TestFalseBranch;
import cn.ideabuffer.process.core.test.nodes.ifs.TestIfRule;
import cn.ideabuffer.process.core.test.nodes.ifs.TestTrueBranch;
import cn.ideabuffer.process.core.test.nodes.trycatch.*;
import cn.ideabuffer.process.core.test.nodes.whiles.*;
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
    public void testInstanceResult() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        definition
            // 注册执行节点
            .addProcessNode(new TestNode1(), new TestNode2())
            // 注册基础节点
            .addBaseNode(new TestBaseNode());
        ProcessInstance<String> instance = new DefaultProcessInstance<>(definition);
        Context context = Contexts.newContext();
        Key<Integer> key = Contexts.newKey("k", int.class);
        context.put(key, 0);

        instance.execute(context);
        // 输出执行结果
        System.out.println(instance.getResult());
    }

    @Test
    public void testBranch() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        definition
            .addBranchNode(Branches.newBranch(new TestNode1(), new TestNode2()).parallel(executorService));
        ProcessInstance<String> instance = new DefaultProcessInstance<>(definition);
        Context context = Contexts.newContext();
        Key<Integer> key = Contexts.newKey("k", int.class);
        context.put(key, 0);

        instance.execute(context);
        Thread.sleep(10000);
    }

    @Test
    public void testIf() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        BranchNode trueBranch = Branches.newBranch(new TestNode1());
        BranchNode falseBranch = Branches.newBranch(new TestNode1());
        Key<Integer> key = Contexts.newKey("k", int.class);
        definition.addIf(Nodes.newIf(ctx -> ctx.get(key, 0) < 5).then(trueBranch)
            .otherwise(falseBranch));
        ProcessInstance<String> instance = new DefaultProcessInstance<>(definition);
        Context context = Contexts.newContext();
        context.put(key, 1);

        instance.execute(context);
    }

    @Test
    public void testWhileContinue() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        TestWhileRule rule = new TestWhileRule();
        definition.addWhile(Nodes.newWhile(rule)
            .then(new TestWhileContinueNode1(), new TestWhileContinueNode2(),
                new TestWhileContinueNode3()));
        ProcessInstance<String> instance = new DefaultProcessInstance<>(definition);
        Context context = Contexts.newContext();

        instance.execute(context);
    }

    @Test
    public void testWhileBreak() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        TestWhileRule rule = new TestWhileRule();
        definition.addWhile(Nodes.newWhile(rule)
            .then(new TestWhileBreakNode1(), new TestWhileBreakNode2(),
                new TestWhileBreakNode3()));
        ProcessInstance<String> instance = new DefaultProcessInstance<>(definition);
        Context context = Contexts.newContext();

        instance.execute(context);
    }

    @Test
    public void testNesting() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        TestWhileRule rule = new TestWhileRule();
        definition.addWhile(Nodes.newWhile((ctx) -> {
            System.out.println("in while --");
            return true;
        }).then(
            Nodes.newWhile(rule)
                .then(
                    new TestWhileContinueNode1(),
                    new TestWhileContinueNode2(),
                    Nodes.newIf(rule).then(
                        Nodes.newIf((ctx) -> true).then(new TestBreakNode()).end(),
                        new TestNode1())
                        .end(),
                    new TestWhileContinueNode3())));
        ProcessInstance<String> instance = new DefaultProcessInstance<>(definition);
        Context context = Contexts.newContext();

        instance.execute(context);
        Thread.sleep(10000);
    }

    @Test
    public void testSerial() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        ProcessInstance<String> instance = new DefaultProcessInstance<>(definition);
        Context context = Contexts.newContext();
        Key<Integer> key = Contexts.newKey("k", int.class);
        context.put(key, 1);
        long start = System.currentTimeMillis();
        instance.execute(context);
        System.out.println("cost:" + (System.currentTimeMillis() - start) / 1000);
    }

    @Test
    public void testTryCatchFinally() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        definition.addProcessNode(Nodes.newTry(new TryNode1(), new TryNode2())
            .catchOn(Exception.class, new CatchNode1(), new CatchNode2())
            .doFinally(new FinallyNode1(), new FinallyNode2()));

        ProcessInstance<String> instance = new DefaultProcessInstance<>(definition);
        Context context = Contexts.newContext();
        Key<Integer> key = Contexts.newKey("k", int.class);
        context.put(key, 1);

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
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();

        ProcessDefinition<String> subDefine = new DefaultProcessDefinition<>();
        TestIfRule rule = new TestIfRule();
        subDefine.addIf(Nodes.newIf(rule).then(new TestTrueBranch())
            .otherwise(new TestFalseBranch()));
        ProcessInstance<String> subInstance = new DefaultProcessInstance<>(subDefine);

        definition.addProcessNode(new TestNode1()).addProcessNode(subInstance).addProcessNode(new TestNode2());

        Context context = Contexts.newContext();
        Key<Integer> key = Contexts.newKey("k", int.class);
        context.put(key, 1);

        ProcessInstance<String> mainInstance = new DefaultProcessInstance<>(definition);

        mainInstance.execute(context);
    }
}
