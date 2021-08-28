package cn.ideabuffer.process.core.test;

import cn.ideabuffer.process.core.*;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.nodes.builder.ProcessNodeBuilder;
import cn.ideabuffer.process.core.processors.builder.ConvertProcessorBuilder;
import cn.ideabuffer.process.core.test.processors.TestProcessor1;
import cn.ideabuffer.process.core.test.processors.TestProcessor2;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConvertProcessorTest {

    @Test
    public void testConvert() throws Exception {
        Key<Integer> resultKey = new Key<>("resultKey", int.class);

        Processor<String> stringProcessor = context -> "hello";
        ResultConverter<String, Integer> converter = (context, origin) -> origin.length();
        ProcessDefinition<Integer> definition = ProcessDefinitionBuilder.<Integer>newBuilder()
            .declaringKeys(resultKey)
            .resultHandler(context -> context.get(resultKey))
            // 注册执行节点
            .addProcessNodes(
                ProcessNodeBuilder.<Integer>newBuilder()
                    // 设置返回结果key
                    .resultKey(resultKey)
                    // 设置Processor
                    .by(
                        ConvertProcessorBuilder.<String, Integer>newBuilder()
                            .processor(stringProcessor)
                            .converter(converter)
                            .build()
                    )
                    .build())
            .build();
        ProcessInstance<Integer> instance = definition.newInstance();
        Context context = Contexts.newContext();

        Integer result = instance.process(context);
        // 输出执行结果
        assertEquals(5L, (long)result);
        definition.destroy();
    }

}
