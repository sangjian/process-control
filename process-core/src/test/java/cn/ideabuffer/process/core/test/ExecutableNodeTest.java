package cn.ideabuffer.process.core.test;

import cn.ideabuffer.process.core.DefaultProcessDefinition;
import cn.ideabuffer.process.core.DefaultProcessInstance;
import cn.ideabuffer.process.core.ProcessDefinition;
import cn.ideabuffer.process.core.ProcessInstance;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.handler.ExceptionHandler;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.nodes.builder.ExecutableNodeBuilder;
import cn.ideabuffer.process.core.rule.Rule;
import cn.ideabuffer.process.core.test.nodes.executable.TestExceptionExecutableNode1;
import cn.ideabuffer.process.core.test.nodes.executable.TestExecutableNode1;
import cn.ideabuffer.process.core.test.nodes.executable.TestExecutableNode2;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author sangjian.sj
 * @date 2020/04/01
 */
public class ExecutableNodeTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void testSimpleExecutableNode() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        definition
            .addProcessNodes(new TestExecutableNode1(), new TestExecutableNode2());
        ProcessInstance<String> instance = new DefaultProcessInstance<>(definition);
        Context context = Contexts.newContext();
        Key<Integer> key = Contexts.newKey("k", int.class);
        context.put(key, 0);

        instance.execute(context);
    }

    @Test
    public void testConfiguredExecutableNode() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        // 执行规则
        Rule rule = (ctx) -> true;
        Executor executor = Executors.newFixedThreadPool(2);
        ExceptionHandler handler = (t) -> logger.error("execute error!", t);
        ExecutableNode node1 = ExecutableNodeBuilder.newBuilder(new TestExceptionExecutableNode1())
            // 设置规则
            .processOn(rule)
            // 设置异常处理器
            .exceptionHandler(handler)
            // 设置并行执行，没有指定线程池，则会开启新线程执行
            .parallel()
            .build();

        ExecutableNode node2 = ExecutableNodeBuilder.newBuilder(new TestExecutableNode2())
            // 设置规则
            .processOn(rule)
            // 设置异常处理器
            .exceptionHandler(handler)
            // 设置并行执行，指定线程池执行
            .parallel(executor)
            .build();
        definition
            .addProcessNodes(node1, node2);
        ProcessInstance<String> instance = new DefaultProcessInstance<>(definition);
        Context context = Contexts.newContext();
        Key<Integer> key = Contexts.newKey("k", int.class);
        context.put(key, 0);
        instance.execute(context);
        Thread.sleep(3000);
    }

}
