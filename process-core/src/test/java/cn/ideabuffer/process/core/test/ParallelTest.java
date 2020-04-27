package cn.ideabuffer.process.core.test;

import cn.ideabuffer.process.core.DefaultProcessDefinition;
import cn.ideabuffer.process.core.ProcessDefinition;
import cn.ideabuffer.process.core.ProcessInstance;
import cn.ideabuffer.process.core.nodes.ParallelBranchNode;
import cn.ideabuffer.process.core.nodes.builder.ParallelBranchNodeBuilder;
import cn.ideabuffer.process.core.strategy.ProceedStrategies;
import cn.ideabuffer.process.core.test.nodes.parallel.TestParallelNode1;
import cn.ideabuffer.process.core.test.nodes.parallel.TestParallelNode2;
import org.junit.Test;

/**
 * @author sangjian.sj
 * @date 2020/03/10
 */
public class ParallelTest {

    @Test
    public void testParallelNode() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        ParallelBranchNode node = ParallelBranchNodeBuilder.newBuilder().addBranch(new TestParallelNode1()).addBranch(
            new TestParallelNode2()).proceedWhen(ProceedStrategies.ALL_PROCEEDED).build();
        definition.addProcessNodes(node);
        ProcessInstance<String> instance = definition.newInstance();

        instance.execute(null);
    }

}