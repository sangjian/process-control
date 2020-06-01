package cn.ideabuffer.process.core.test;

import cn.ideabuffer.process.core.DefaultProcessDefinition;
import cn.ideabuffer.process.core.ProcessDefinition;
import cn.ideabuffer.process.core.ProcessInstance;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.nodes.ParallelBranchNode;
import cn.ideabuffer.process.core.nodes.ProcessNode;
import cn.ideabuffer.process.core.nodes.builder.ParallelBranchNodeBuilder;
import cn.ideabuffer.process.core.processors.impl.ParallelBranchProcessorImpl;
import cn.ideabuffer.process.core.strategy.ProceedStrategies;
import cn.ideabuffer.process.core.test.nodes.parallel.TestParallelNodeProcessor1;
import cn.ideabuffer.process.core.test.nodes.parallel.TestParallelNodeProcessor2;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

/**
 * @author sangjian.sj
 * @date 2020/03/10
 */
public class ParallelTest {

    @Test
    public void testParallelNode() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        Thread mainThread = Thread.currentThread();
        ParallelBranchNode node = ParallelBranchNodeBuilder.newBuilder()
            .addBranch(context -> {
                assertNotEquals(mainThread, Thread.currentThread());
                return null;
            })
            .addBranch(context -> {
                assertNotEquals(mainThread, Thread.currentThread());
                return null;
            })
            .by(new ParallelBranchProcessorImpl())
            .proceedWhen(ProceedStrategies.ALL_PROCEEDED).build();
        definition.addProcessNodes(node);
        ProcessInstance<String> instance = definition.newInstance();

        instance.execute(Contexts.newContext());
    }

}
