package cn.ideabuffer.process.core.test;

import cn.ideabuffer.process.core.DefaultProcessDefinition;
import cn.ideabuffer.process.core.ProcessDefinition;
import cn.ideabuffer.process.core.ProcessInstance;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.nodes.ProcessNode;
import cn.ideabuffer.process.core.nodes.builder.ProcessNodeBuilder;
import cn.ideabuffer.process.core.rule.Rule;
import cn.ideabuffer.process.core.status.ProcessStatus;
import cn.ideabuffer.process.core.test.nodes.executable.TestExecutableNodeProcessor1;
import cn.ideabuffer.process.core.test.nodes.executable.TestExecutableNodeProcessor2;
import org.awaitility.Duration;
import org.junit.Test;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;

/**
 * @author sangjian.sj
 * @date 2020/04/01
 */
public class ExecutableNodeTest {

    @Test
    public void testSimpleExecutableNode() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        definition
            .addProcessNodes(new ProcessNode<>(new TestExecutableNodeProcessor1()),
                new ProcessNode<>(new TestExecutableNodeProcessor2()));
        ProcessInstance<String> instance = definition.newInstance();
        Context context = Contexts.newContext();
        Key<Integer> key = Contexts.newKey("k", int.class);
        context.put(key, 1);

        instance.execute(context);
        assertEquals(5, (int)context.get(key));
    }

    @Test
    public void testConfiguredExecutableNode() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        // 执行规则
        Rule rule = (ctx) -> true;
        Executor executor = Executors.newFixedThreadPool(2);
        ProcessNode<ProcessStatus> node1 = ProcessNodeBuilder.<ProcessStatus>newBuilder()
            // 设置规则
            .processOn(rule)
            // 设置并行执行，没有指定线程池，则会开启新线程执行
            .parallel()
            // 直接抛出异常
            .by(context -> {
                throw new RuntimeException("test exception!");
            })
            .build();

        ProcessNode<ProcessStatus> node2 = ProcessNodeBuilder.<ProcessStatus>newBuilder()
            // 设置规则
            .processOn(rule)
            // 设置并行执行，指定线程池执行
            .parallel(executor)
            .by(context -> {
                Key<Integer> key = Contexts.newKey("k", int.class);
                // 设置key的值为5
                context.put(key, 5);
                return ProcessStatus.proceed();
            })
            .build();
        definition
            .addProcessNodes(node1, node2);
        ProcessInstance<String> instance = definition.newInstance();
        Context context = Contexts.newContext();
        Key<Integer> key = Contexts.newKey("k", int.class);
        // 初始化key的值为1
        context.put(key, 1);
        instance.execute(context);

        // 这里等待异步执行，如果key的值为5，说明异步执行不节点之间不影响
        await().atMost(Duration.ONE_SECOND).until(() -> 5 == context.get(key));
        assertEquals(5, (int)context.get(key));
    }

}
