package cn.ideabuffer.process.core.test;

import cn.ideabuffer.process.core.DefaultProcessDefinition;
import cn.ideabuffer.process.core.DefaultProcessInstance;
import cn.ideabuffer.process.core.ProcessDefinition;
import cn.ideabuffer.process.core.ProcessInstance;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.nodes.Nodes;
import cn.ideabuffer.process.core.nodes.ProcessNode;
import cn.ideabuffer.process.core.nodes.builder.ProcessNodeBuilder;
import cn.ideabuffer.process.core.rule.Rule;
import cn.ideabuffer.process.core.rule.Rules;
import cn.ideabuffer.process.core.status.ProcessStatus;
import cn.ideabuffer.process.core.test.nodes.TestProcessor1;
import cn.ideabuffer.process.core.test.nodes.TestProcessor2;
import cn.ideabuffer.process.core.test.nodes.rule.TestRuleNodeProcessor1;
import org.junit.Test;

/**
 * @author sangjian.sj
 * @date 2020/04/01
 */
public class RuleTest {

    @Test
    public void testRule() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        // 注册一个执行节点，并设置规则
        definition.addProcessNodes(ProcessNodeBuilder.<ProcessStatus>newBuilder().processOn((ctx) -> false)
            .by(new TestRuleNodeProcessor1()).build());
        ProcessInstance<String> instance = new DefaultProcessInstance<>(definition);
        Context context = Contexts.newContext();

        instance.execute(context);
    }

    @Test
    public void testAndRule() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        Rule tRule = (ctx) -> true;
        Rule fRule = (ctx) -> false;
        // 注册一个执行节点，并设置规则
        definition.addProcessNodes(ProcessNodeBuilder.<ProcessStatus>newBuilder().processOn(Rules.and(tRule, fRule))
            .by(new TestRuleNodeProcessor1()).build());
        ProcessInstance<String> instance = new DefaultProcessInstance<>(definition);
        Context context = Contexts.newContext();

        instance.execute(context);
    }

    @Test
    public void testIfAndRule() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        definition.addIf(Nodes.newIf(Rules.and((ctx) -> true, (ctx) -> false))
            .then(new ProcessNode<>(new TestRuleNodeProcessor1()))
            .otherwise(new ProcessNode<>(new TestProcessor2())));
        ProcessInstance<String> instance = new DefaultProcessInstance<>(definition);
        Context context = Contexts.newContext();

        instance.execute(context);
    }

    @Test
    public void testOrRule() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        definition.addIf(Nodes.newIf(Rules.or((ctx) -> true, (ctx) -> false))
            .then(new ProcessNode<>(new TestProcessor1()))
            .otherwise(new ProcessNode<>(new TestProcessor2())));
        ProcessInstance<String> instance = new DefaultProcessInstance<>(definition);
        Context context = Contexts.newContext();

        instance.execute(context);
    }

    @Test
    public void testNotRule() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        definition.addIf(Nodes.newIf(Rules.not((ctx) -> true)).then(new ProcessNode<>(new TestProcessor1()))
            .otherwise(new ProcessNode<>(new TestProcessor2())));

        ProcessInstance<String> instance = new DefaultProcessInstance<>(definition);
        Context context = Contexts.newContext();

        instance.execute(context);
    }

}
