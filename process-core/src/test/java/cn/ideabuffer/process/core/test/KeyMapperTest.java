package cn.ideabuffer.process.core.test;

import cn.ideabuffer.process.core.DefaultProcessDefinition;
import cn.ideabuffer.process.core.ProcessDefinition;
import cn.ideabuffer.process.core.ProcessInstance;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.context.KeyMapper;
import cn.ideabuffer.process.core.nodes.Nodes;
import cn.ideabuffer.process.core.test.nodes.TestBaseNodeProcessor;
import cn.ideabuffer.process.core.test.nodes.mapper.TestKeyMapperProcessor1;
import cn.ideabuffer.process.core.test.nodes.mapper.TestKeyMapperProcessor2;
import cn.ideabuffer.process.core.test.nodes.mapper.TestKeyMapperProcessor3;
import org.junit.Test;

/**
 * @author sangjian.sj
 * @date 2020/05/21
 */
public class KeyMapperTest {

    @Test
    public void testMapping() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();

        KeyMapper mapper = new KeyMapper();
        Key<Integer> from = Contexts.newKey("newK", int.class);
        Key<Integer> to = Contexts.newKey("k", int.class);
        mapper.map(from, to);

        definition
            // 注册执行节点
            .addProcessNodes(Nodes.newProcessNode(new TestKeyMapperProcessor1()),
                Nodes.newProcessNode(new TestKeyMapperProcessor2(), mapper),
                Nodes.newProcessNode(new TestKeyMapperProcessor3()))
            // 注册基础节点
            .addBaseNode(Nodes.newBaseNode(new TestBaseNodeProcessor()));
        ProcessInstance<String> instance = definition.newInstance();
        Context context = Contexts.newContext();
        context.put(to, 123);

        instance.execute(context);
        System.out.println(context.get(to));
        // 输出执行结果
        System.out.println(instance.getResult());
    }

}
