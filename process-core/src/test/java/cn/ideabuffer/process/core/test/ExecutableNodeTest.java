package cn.ideabuffer.process.core.test;

import cn.ideabuffer.process.core.DefaultProcessDefinition;
import cn.ideabuffer.process.core.DefaultProcessInstance;
import cn.ideabuffer.process.core.ProcessDefinition;
import cn.ideabuffer.process.core.ProcessInstance;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.handler.ExceptionHandler;
import cn.ideabuffer.process.core.nodes.ProcessNode;
import cn.ideabuffer.process.core.nodes.builder.ProcessNodeBuilder;
import cn.ideabuffer.process.core.rule.Rule;
import cn.ideabuffer.process.core.status.ProcessStatus;
import cn.ideabuffer.process.core.test.nodes.executable.TestExceptionProcessor1;
import cn.ideabuffer.process.core.test.nodes.executable.TestExecutableNodeProcessor1;
import cn.ideabuffer.process.core.test.nodes.executable.TestExecutableNodeProcessor2;
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
            .addProcessNodes(new ProcessNode<>(new TestExecutableNodeProcessor1()),
                new ProcessNode<>(new TestExecutableNodeProcessor2()));
        ProcessInstance<String> instance = definition.newInstance();
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
        ProcessNode<ProcessStatus> node1 = ProcessNodeBuilder.<ProcessStatus>newBuilder()
            // 设置规则
            .processOn(rule)
            // 设置并行执行，没有指定线程池，则会开启新线程执行
            .parallel()
            .by(new TestExceptionProcessor1())
            .build();

        ProcessNode<ProcessStatus> node2 = ProcessNodeBuilder.<ProcessStatus>newBuilder()
            // 设置规则
            .processOn(rule)
            // 设置并行执行，指定线程池执行
            .parallel(executor)
            .by(new TestExecutableNodeProcessor2())
            .build();
        definition
            .addProcessNodes(node1, node2);
        ProcessInstance<String> instance = definition.newInstance();
        Context context = Contexts.newContext();
        Key<Integer> key = Contexts.newKey("k", int.class);
        context.put(key, 0);
        instance.execute(context);
        Thread.sleep(3000);
    }

}
