package cn.ideabuffer.process.core.test;

import cn.ideabuffer.process.core.DefaultProcessDefinition;
import cn.ideabuffer.process.core.ProcessDefinition;
import cn.ideabuffer.process.core.ProcessDefinitionBuilder;
import cn.ideabuffer.process.core.ProcessInstance;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.nodes.Nodes;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.nodes.builder.BranchNodeBuilder;
import cn.ideabuffer.process.core.nodes.builder.IfNodeBuilder;
import cn.ideabuffer.process.core.nodes.builder.ProcessNodeBuilder;
import cn.ideabuffer.process.core.nodes.builder.TryCatchFinallyNodeBuilder;
import cn.ideabuffer.process.core.nodes.condition.IfConditionNode;
import cn.ideabuffer.process.core.status.ProcessStatus;
import cn.ideabuffer.process.core.test.processors.TestProcessor1;
import cn.ideabuffer.process.core.test.processors.TestProcessor2;
import cn.ideabuffer.process.core.test.processors.ifs.TestFalseBranch;
import cn.ideabuffer.process.core.test.processors.ifs.TestIfRule;
import cn.ideabuffer.process.core.test.processors.ifs.TestTrueBranch;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
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
        Key<Integer> resultKey = new Key<>("resultKey", int.class);
//        ProcessDefinition<Integer> definition = new DefaultProcessDefinition<>(resultKey);

        Key<Integer> key = Contexts.newKey("k", int.class);
        ProcessDefinition<Integer> definition = ProcessDefinitionBuilder.<Integer>newBuilder()
            .declaringKeys(resultKey, key)
            .resultHandler(context -> context.get(resultKey))
            // 注册执行节点
            .addProcessNodes(
                ProcessNodeBuilder.<Integer>newBuilder()
                    // 设置返回结果key
                    .resultKey(resultKey)
                    // 设置Processor
                    .by(new TestProcessor2())
                    // 返回条件
                    .returnOn(result -> true)
                    // 注册可读的key
                    .readableKeys(key)
                    .build(),
                ProcessNodeBuilder.<Integer>newBuilder()
                    .resultKey(resultKey)
                    .by(new TestProcessor1())
                    .returnOn(result -> false)
                    .readableKeys(key)
                    .build())
            .build();
        ProcessInstance<Integer> instance = definition.newInstance();
        Context context = Contexts.newContext();
        context.put(key, 0);

        instance.execute(context);
        // 输出执行结果
        assertEquals(2L, (long)instance.getResult());
        definition.destroy();
    }

    @Test
    public void testInstanceResult2() throws Exception {
        Key<Integer> resultKey = new Key<>("resultKey", int.class);
//        ProcessDefinition<Integer> definition = new DefaultProcessDefinition<>(resultKey);

        Key<Integer> key = Contexts.newKey("k", int.class);
        ProcessDefinition<Integer> definition = ProcessDefinitionBuilder.<Integer>newBuilder()
            .declaringKeys(resultKey, key)
            .resultHandler(context -> context.get(resultKey))
            // 注册执行节点
            .addProcessNodes(
                ProcessNodeBuilder.<Integer>newBuilder()
                    .resultKey(resultKey)
                    .by(new TestProcessor2())
                    .returnOn(result -> false)
                    .readableKeys(key)
                    .build(),
                ProcessNodeBuilder.<Integer>newBuilder()
                    .resultKey(resultKey)
                    .by(new TestProcessor1())
                    .returnOn(result -> true)
                    .readableKeys(key)
                    .build())
            .build();
        ProcessInstance<Integer> instance = definition.newInstance();
        Context context = Contexts.newContext();
        context.put(key, 0);

        instance.execute(context);
        // 输出执行结果
        assertEquals(1L, (long)instance.getResult());
    }

    @Test
    public void testBranch() throws Exception {
//        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        Key<Integer> key = Contexts.newKey("k", int.class);
        ProcessDefinition<String> definition = ProcessDefinitionBuilder.<String>newBuilder()
            .declaringKeys(key)
            .addBranchNode(Nodes.newBranch(
                ProcessNodeBuilder.<ProcessStatus>newBuilder()
                    .by(context -> {
                        context.put(key, 1);
                        return ProcessStatus.proceed();
                    })
                    .returnOn(result -> false)
                    .writableKeys(key)
                    .build(),
                ProcessNodeBuilder.<ProcessStatus>newBuilder()
                    .by(context -> {
                        context.put(key, 2);
                        return ProcessStatus.proceed();
                    })
                    .returnOn(result -> false)
                    .writableKeys(key)
                    .build()))
            .build();
        ProcessInstance<String> instance = definition.newInstance();
        Context context = Contexts.newContext();
        context.put(key, 0);
        instance.execute(context);

        assertEquals(2, (int)context.get(key));
    }

    @Test
    public void testBranchWithExecutor() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
//        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        Thread mainThread = Thread.currentThread();
        CountDownLatch latch = new CountDownLatch(2);
        BranchNode branchNode = BranchNodeBuilder.newBuilder().addNodes(
            Nodes.newProcessNode(context -> {
                assertNotSame(mainThread, Thread.currentThread());
                latch.countDown();
                return ProcessStatus.proceed();
            }),
            Nodes.newProcessNode(context -> {
                assertNotSame(mainThread, Thread.currentThread());
                latch.countDown();
                return ProcessStatus.proceed();
            }))
            .parallel(executorService)
            .build();
        ProcessDefinition<String> definition = ProcessDefinitionBuilder.<String>newBuilder()
            .addBranchNode(branchNode)
            .build();
        ProcessInstance<String> instance = definition.newInstance();
        Context context = Contexts.newContext();
        instance.execute(context);
        latch.await();
    }

    @Test
    public void testTryCatchFinally() throws Exception {
//        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        AtomicBoolean processor1Flag = new AtomicBoolean();
        AtomicBoolean processor2Flag = new AtomicBoolean();
        AtomicBoolean processor3Flag = new AtomicBoolean();

        AtomicBoolean catchProcessor1Flag = new AtomicBoolean();
        AtomicBoolean catchProcessor2Flag = new AtomicBoolean();

        AtomicBoolean finallyProcessor1Flag = new AtomicBoolean();
        AtomicBoolean finallyProcessor2Flag = new AtomicBoolean();
        ProcessDefinition<String> definition = ProcessDefinitionBuilder.<String>newBuilder()
            .addProcessNodes(
            TryCatchFinallyNodeBuilder.newBuilder()
                .tryOn(Nodes.newProcessNode(context -> {
                        processor1Flag.set(true);
                        return null;
                    }),
                    Nodes.newProcessNode(context -> {
                        processor2Flag.set(true);
                        throw new NullPointerException();
                    }),
                    Nodes.newProcessNode(context -> {
                        processor3Flag.set(true);
                        return null;
                    }))
                .catchOn(Exception.class, Nodes.newProcessNode(context -> {
                    catchProcessor1Flag.set(true);
                    return null;
                }), Nodes.newProcessNode(context -> {
                    catchProcessor2Flag.set(true);
                    return null;
                }))
                .doFinally(Nodes.newProcessNode(context -> {
                    finallyProcessor1Flag.set(true);
                    return null;
                }), Nodes.newProcessNode(context -> {
                    finallyProcessor2Flag.set(true);
                    return null;
                }))
                .build())
            .build();

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
//        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();

        Key<Integer> key = Contexts.newKey("k", int.class);
//        ProcessDefinition<String> subDefine = new DefaultProcessDefinition<>();
        TestIfRule rule = new TestIfRule();
        IfConditionNode ifConditionNode = IfNodeBuilder.newBuilder()
            .processOn(rule)
            .readableKeys(key)
            .then(new TestTrueBranch())
            .otherwise(new TestFalseBranch())
            .build();
        ProcessDefinition<String> subDefine = ProcessDefinitionBuilder.<String>newBuilder()
            .declaringKeys(key)
            .addIf(ifConditionNode)
            .build();
        ProcessInstance<String> subInstance = subDefine.newInstance();
        ProcessDefinition<String> definition = ProcessDefinitionBuilder.<String>newBuilder()
            .declaringKeys(key)
            .addProcessNodes(
                ProcessNodeBuilder.<Integer>newBuilder()
                    .readableKeys(key)
                    .by(new TestProcessor1())
                    .build())
            .addProcessNodes(subInstance)
            .addProcessNodes(ProcessNodeBuilder.<Integer>newBuilder()
                .readableKeys(key)
                .by(new TestProcessor2())
                .build())
            .build();

        Context context = Contexts.newContext();
        context.put(key, 1);

        ProcessInstance<String> mainInstance = definition.newInstance();

        mainInstance.execute(context);
    }

    @Test
    public void testKey() {
        Key<List<String>> k = new Key<>("k", List.class);
        Context context = Contexts.newContext();
        context.put(k, new ArrayList<>());
    }
}
