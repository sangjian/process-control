package cn.ideabuffer.process.core.test;

import cn.ideabuffer.process.core.DefaultProcessDefinition;
import cn.ideabuffer.process.core.ProcessDefinition;
import cn.ideabuffer.process.core.ProcessInstance;
import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.nodes.Nodes;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.nodes.builder.BranchNodeBuilder;
import cn.ideabuffer.process.core.processors.StatusProcessor;
import cn.ideabuffer.process.core.status.ProcessStatus;
import cn.ideabuffer.process.core.test.nodes.TestBaseNodeProcessor;
import cn.ideabuffer.process.core.test.nodes.TestProcessor1;
import cn.ideabuffer.process.core.test.nodes.TestProcessor2;
import cn.ideabuffer.process.core.test.nodes.ifs.TestFalseBranch;
import cn.ideabuffer.process.core.test.nodes.ifs.TestIfRule;
import cn.ideabuffer.process.core.test.nodes.ifs.TestTrueBranch;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public class ProcessInstanceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessInstanceTest.class);

    @Test
    public void testInstanceResult() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();

        definition
            // 注册执行节点
            .addProcessNodes(Nodes.newProcessNode(new TestProcessor1()), Nodes.newProcessNode(new TestProcessor2()));
            // 注册基础节点
            //.addBaseNode(Nodes.newBaseNode(new TestBaseNodeProcessor()));
        ProcessInstance<String> instance = definition.newInstance();
        Context context = Contexts.newContext();
        Key<Integer> key = Contexts.newKey("k", int.class);
        context.put(key, 0);

        instance.execute(context);
        // 输出执行结果
        assertEquals("TestBaseNodeProcessor", instance.getResult());
    }

    @Test
    public void testBranch() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        definition
            .addBranchNode(Nodes.newBranch((StatusProcessor)context -> {
                Key<Integer> key = Contexts.newKey("k", int.class);
                context.put(key, 1);
                return ProcessStatus.PROCEED;
            }, context -> {
                Key<Integer> key = Contexts.newKey("k", int.class);
                context.put(key, 2);
                return ProcessStatus.PROCEED;
            }));
        ProcessInstance<String> instance = definition.newInstance();
        Key<Integer> key = Contexts.newKey("k", int.class);
        Context context = Contexts.newContext();
        context.put(key, 0);
        instance.execute(context);

        assertEquals(2, (int)context.get(key));
    }

    @Test
    public void testBranchWithExecutor() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        Thread mainThread = Thread.currentThread();
        CountDownLatch latch = new CountDownLatch(2);
        BranchNode branchNode = BranchNodeBuilder.newBuilder().addNodes(
            Nodes.newProcessNode(context -> {
                assertNotSame(mainThread, Thread.currentThread());
                latch.countDown();
                return ProcessStatus.PROCEED;
            }),
            Nodes.newProcessNode(context -> {
                assertNotSame(mainThread, Thread.currentThread());
                latch.countDown();
                return ProcessStatus.PROCEED;
            }))
            .parallel(executorService)
            .build();
        definition
            .addBranchNode(branchNode);
        ProcessInstance<String> instance = definition.newInstance();
        Context context = Contexts.newContext();
        instance.execute(context);
        latch.await();
    }

    @Test
    public void testTryCatchFinally() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        AtomicBoolean processor1Flag = new AtomicBoolean();
        AtomicBoolean processor2Flag = new AtomicBoolean();
        AtomicBoolean processor3Flag = new AtomicBoolean();

        AtomicBoolean catchProcessor1Flag = new AtomicBoolean();
        AtomicBoolean catchProcessor2Flag = new AtomicBoolean();

        AtomicBoolean finallyProcessor1Flag = new AtomicBoolean();
        AtomicBoolean finallyProcessor2Flag = new AtomicBoolean();
        definition.addProcessNodes(
            Nodes.newTry((Processor<Void>)context -> {
                processor1Flag.set(true);
                return null;
            }, context -> {
                processor2Flag.set(true);
                throw new NullPointerException();
            }, context -> {
                processor3Flag.set(true);
                return null;
            }).catchOn(Exception.class, (Processor<Void>)context -> {
                catchProcessor1Flag.set(true);
                return null;
            }, context -> {
                catchProcessor2Flag.set(true);
                return null;
            }).doFinally((Processor<Void>)context -> {
                finallyProcessor1Flag.set(true);
                return null;
            }, context -> {
                finallyProcessor2Flag.set(true);
                return null;
            }));

        ProcessInstance<String> instance = definition.newInstance();
        Context context = Contexts.newContext();

        instance.execute(context);

        assertTrue(processor1Flag.get());
        assertTrue(processor2Flag.get());
        assertFalse(processor3Flag.get());

        assertTrue(catchProcessor1Flag.get());
        assertTrue(catchProcessor2Flag.get());

        assertTrue(finallyProcessor1Flag.get());
        assertTrue(finallyProcessor2Flag.get());
    }

    @Test
    public void testSubChain() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();

        ProcessDefinition<String> subDefine = new DefaultProcessDefinition<>();
        TestIfRule rule = new TestIfRule();
        subDefine.addIf(Nodes.newIf(rule).then(new TestTrueBranch())
            .otherwise(new TestFalseBranch()));
        ProcessInstance<String> subInstance = subDefine.newInstance();

        definition.addProcessNodes(Nodes.newProcessNode(new TestProcessor1())).addProcessNodes(subInstance)
            .addProcessNodes(Nodes.newProcessNode(new TestProcessor2()));

        Context context = Contexts.newContext();
        Key<Integer> key = Contexts.newKey("k", int.class);
        context.put(key, 1);

        ProcessInstance<String> mainInstance = definition.newInstance();

        mainInstance.execute(context);
    }
}
