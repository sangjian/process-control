package cn.ideabuffer.process.core.test;

import cn.ideabuffer.process.core.DefaultProcessDefinition;
import cn.ideabuffer.process.core.ProcessDefinition;
import cn.ideabuffer.process.core.ProcessDefinitionBuilder;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.nodes.ProcessNode;
import cn.ideabuffer.process.core.nodes.builder.ProcessNodeBuilder;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author sangjian.sj
 * @date 2020/08/17
 */
public class EnabledTest {

    private static boolean ENABLE = false;

    @Test
    public void testEnable() throws Exception {
//        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        AtomicBoolean flag = new AtomicBoolean();
        ProcessNode<ProcessStatus> node = ProcessNodeBuilder.<ProcessStatus>newBuilder()
            .by(context -> {
                flag.set(true);
                return ProcessStatus.proceed();
            })
            .enabled(() -> ENABLE)
            .build();
        ProcessDefinition<String> definition = ProcessDefinitionBuilder.<String>newBuilder()
            .addProcessNodes(node)
            .build();
        Context context = Contexts.newContext();
        definition.newInstance().execute(context);
        assertFalse(flag.get());
        assertFalse(node.enabled());
        ENABLE = true;
        definition.newInstance().execute(context);
        assertTrue(flag.get());
        assertTrue(node.enabled());
    }
}
