package cn.ideabuffer.process.core.test;

import cn.ideabuffer.process.core.DefaultProcessDefinition;
import cn.ideabuffer.process.core.ProcessDefinition;
import cn.ideabuffer.process.core.ProcessInstance;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.nodes.Nodes;
import cn.ideabuffer.process.core.nodes.builder.ProcessNodeBuilder;
import cn.ideabuffer.process.core.rules.Rule;
import cn.ideabuffer.process.core.rules.Rules;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author sangjian.sj
 * @date 2020/04/01
 */
public class RuleTest {

    @Test
    public void testTrueRule() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        AtomicBoolean executed = new AtomicBoolean(false);
        // 注册一个执行节点，并设置规则
        definition.addProcessNodes(ProcessNodeBuilder.<ProcessStatus>newBuilder().processOn((ctx) -> true)
            .by(context -> {
                executed.set(true);
                return null;
            }).build());
        ProcessInstance<String> instance = definition.newInstance();
        Context context = Contexts.newContext();

        instance.execute(context);

        assertTrue(executed.get());
    }

    @Test
    public void testFalseRule() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        AtomicBoolean executed = new AtomicBoolean(false);
        // 注册一个执行节点，并设置规则
        definition.addProcessNodes(ProcessNodeBuilder.<ProcessStatus>newBuilder().processOn((ctx) -> false)
            .by(context -> {
                executed.set(true);
                return null;
            }).build());
        ProcessInstance<String> instance = definition.newInstance();
        Context context = Contexts.newContext();

        instance.execute(context);
        assertFalse(executed.get());
    }

    @Test
    public void testAndRule() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        Rule tRule = (ctx) -> true;
        Rule fRule = (ctx) -> false;
        AtomicBoolean executed = new AtomicBoolean(false);
        // 注册一个执行节点，并设置规则
        definition.addProcessNodes(ProcessNodeBuilder.<ProcessStatus>newBuilder().processOn(Rules.and(tRule, fRule))
            .by(context -> {
                executed.set(true);
                return null;
            }).build());
        ProcessInstance<String> instance = definition.newInstance();
        Context context = Contexts.newContext();

        instance.execute(context);

        assertFalse(executed.get());
    }

    @Test
    public void testIfAndRule() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        AtomicBoolean processor1Flag = new AtomicBoolean(false);
        AtomicBoolean processor2Flag = new AtomicBoolean(false);
        definition.addIf(Nodes.newIf(Rules.and((ctx) -> true, (ctx) -> false))
            .then(Nodes.newProcessNode(context -> {
                processor1Flag.set(true);
                return null;
            })).otherwise(Nodes.newProcessNode(context -> {
                processor2Flag.set(true);
                return null;
            })));
        ProcessInstance<String> instance = definition.newInstance();
        Context context = Contexts.newContext();

        instance.execute(context);

        assertFalse(processor1Flag.get());
        assertTrue(processor2Flag.get());
    }

    @Test
    public void testOrRule() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        AtomicBoolean processor1Flag = new AtomicBoolean(false);
        AtomicBoolean processor2Flag = new AtomicBoolean(false);
        definition.addIf(
            Nodes.newIf(Rules.or((ctx) -> true, (ctx) -> false))
                .then(Nodes.newProcessNode(context -> {
                    processor1Flag.set(true);
                    return null;
                })).otherwise(Nodes.newProcessNode(context -> {
                processor2Flag.set(true);
                return null;
            })));
        ProcessInstance<String> instance = definition.newInstance();
        Context context = Contexts.newContext();

        instance.execute(context);

        assertTrue(processor1Flag.get());
        assertFalse(processor2Flag.get());
    }

    @Test
    public void testNotRule() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        AtomicBoolean processor1Flag = new AtomicBoolean(false);
        AtomicBoolean processor2Flag = new AtomicBoolean(false);
        definition.addIf(
            Nodes.newIf(Rules.not((ctx) -> true))
                .then(Nodes.newProcessNode(context -> {
                    processor1Flag.set(true);
                    return null;
                }))
                .otherwise(Nodes.newProcessNode(context -> {
                    processor2Flag.set(true);
                    return null;
                })));

        ProcessInstance<String> instance = definition.newInstance();
        Context context = Contexts.newContext();

        instance.execute(context);

        assertFalse(processor1Flag.get());
        assertTrue(processor2Flag.get());
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(1);
        list.add(7);
        list.add(2);

        Integer m = list.stream().min(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        }).get();

        System.out.println(m);
    }

}
