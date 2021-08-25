package cn.ideabuffer.process.core.test;

import cn.ideabuffer.process.core.DefaultProcessDefinition;
import cn.ideabuffer.process.core.ProcessDefinition;
import cn.ideabuffer.process.core.ProcessDefinitionBuilder;
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
//        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();

        KeyMapper mapper = new KeyMapper();
        Key<Integer> newKey = Contexts.newKey("newKey", int.class);
        Key<Integer> oldKey = Contexts.newKey("oldKey", int.class);
        mapper.map(oldKey, newKey);

        ProcessDefinition<String> definition = ProcessDefinitionBuilder.<String>newBuilder()
            .declaringKeys(newKey, oldKey)
            // 注册执行节点
            .addProcessNodes(ProcessNodeBuilder.<Void>newBuilder().by(context -> {
                    assertNull(null, context.get(oldKey));
                    return null;
                }).readableKeys(oldKey).build(),
                ProcessNodeBuilder.<Void>newBuilder()
                    .by(context -> {
                        // 指定了mapper，这里取newKey与取oldK相同
                        assertEquals(123, (int)context.get(newKey));
                        assertEquals(123, (int)context.get(oldKey));
                        // 设置newKey的值与设置old的值相同
                        context.put(newKey, 456);
                        assertEquals(456, (int)context.get(newKey));
                        assertEquals(456, (int)context.get(oldKey));
                        return null;
                    })
                    .readableKeys(newKey, oldKey)
                    .writableKeys(newKey, oldKey)
                    .keyMapper(mapper)
                    .build(),
                ProcessNodeBuilder.<Void>newBuilder().by(context -> {
                        // 没有指定mapper，找不到对应的key
                        assertNull(context.get(oldKey));
                        context.put(oldKey, 500);
                        return null;
                    })
                    .readableKeys(oldKey)
                    .writableKeys(oldKey)
                    .build()
            )
            .build();
        ProcessInstance<String> instance = definition.newInstance();
        Context context = Contexts.newContext();
        context.put(newKey, 123);

        instance.execute(context);
        assertEquals(456, (int)context.get(newKey));
        assertEquals(500, (int)context.get(oldKey));
    }

}
