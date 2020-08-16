package cn.ideabuffer.process.core.test;

import cn.ideabuffer.process.core.DefaultProcessDefinition;
import cn.ideabuffer.process.core.ProcessDefinition;
import cn.ideabuffer.process.core.ProcessInstance;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.context.KeyMapper;
import cn.ideabuffer.process.core.nodes.builder.ProcessNodeBuilder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
            .addProcessNodes(ProcessNodeBuilder.<Void>newBuilder().by(context -> {
                    assertEquals(123, (int)context.get(to));
                    return null;
                }).readableKeys(to).build(),
                ProcessNodeBuilder.<Void>newBuilder().by(context -> {
                    // 指定了mapper，这里取from与取to相同
                    assertEquals(123, (int)context.get(from));
                    assertEquals(123, (int)context.get(to));
                    // 设置from的值与设置to的值相同
                    context.put(from, 456);
                    assertEquals(456, (int)context.get(from));
                    assertEquals(456, (int)context.get(to));
                    return null;
                }).readableKeys(from, to).writableKeys(from).keyMapper(mapper).build(),
                ProcessNodeBuilder.<Void>newBuilder().by(context -> {
                    // 没有指定mapper，找不到对应的key
                    assertNull(context.get(from));
                    context.put(from, 456);
                    return null;
                }).readableKeys(from).writableKeys(from).build());
        ProcessInstance<String> instance = definition.newInstance();
        Context context = Contexts.newContext();
        context.put(to, 123);

        instance.execute(context);
        assertEquals(456, (int)context.get(to));
    }

}
