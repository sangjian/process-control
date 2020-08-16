package cn.ideabuffer.process.core.test;

import cn.ideabuffer.process.core.DefaultProcessDefinition;
import cn.ideabuffer.process.core.ProcessDefinition;
import cn.ideabuffer.process.core.ProcessInstance;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.exception.KeyNotWritableException;
import cn.ideabuffer.process.core.exception.UnreadableKeyException;
import cn.ideabuffer.process.core.nodes.builder.ProcessNodeBuilder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author sangjian.sj
 * @date 2020/08/16
 */
public class KeyCheckTest {

    @Test(expected = UnreadableKeyException.class)
    public void unreadableKeyTest() throws Exception {
        ProcessDefinition<Void> definition = new DefaultProcessDefinition<>();
        Key<Integer> key = Contexts.newKey("k", Integer.class);
        definition.addProcessNodes(ProcessNodeBuilder.<Void>newBuilder()
            .by(context -> {
                context.get(key);
                return null;
            }).build()
        );
        definition.newInstance().execute(Contexts.newContext());
    }

    @Test(expected = KeyNotWritableException.class)
    public void keyNotWritableTest() throws Exception {
        ProcessDefinition<Void> definition = new DefaultProcessDefinition<>();
        Key<Integer> key = Contexts.newKey("k", Integer.class);
        definition.addProcessNodes(ProcessNodeBuilder.<Void>newBuilder()
            .by(context -> {
                context.put(key, 67);
                return null;
            }).build()
        );
        definition.newInstance().execute(Contexts.newContext());
    }

    @Test
    public void resultKeyTest() throws Exception {
        Key<Integer> key = Contexts.newKey("k", Integer.class);
        ProcessDefinition<Integer> definition = new DefaultProcessDefinition<>(key);
        definition.addProcessNodes(ProcessNodeBuilder.<Integer>newBuilder()
            .by(context -> {
                context.put(key, 67);
                return context.get(key);
            })
            .resultKey(key)
            .readableKeys(key)
            .returnOn(result -> result != null && result % 2 == 1)
            .build()
        );
        ProcessInstance<Integer> instance = definition.newInstance();
        instance.execute(Contexts.newContext());
        Integer result = instance.getResult();
        assertEquals(Integer.valueOf(67), result);
    }

}
