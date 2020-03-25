package cn.ideabuffer.process.test;

import cn.ideabuffer.process.DefaultProcessDefinition;
import cn.ideabuffer.process.DefaultProcessInstance;
import cn.ideabuffer.process.ProcessDefinition;
import cn.ideabuffer.process.ProcessInstance;
import cn.ideabuffer.process.nodes.Nodes;
import cn.ideabuffer.process.nodes.ParallelBranchNode;
import cn.ideabuffer.process.test.nodes.parallel.TestParallelNode1;
import cn.ideabuffer.process.test.nodes.parallel.TestParallelNode2;
import cn.ideabuffer.process.strategy.ProceedStrategies;
import org.junit.Test;

/**
 * @author sangjian.sj
 * @date 2020/03/10
 */
public class ParallelTest {

    @Test
    public void testParallelNode() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        ParallelBranchNode node = Nodes.newParallelBranchNode();
        definition.addProcessNode(node.addBranch(new TestParallelNode1()).addBranch(new TestParallelNode2())
            .proceedWhen(ProceedStrategies.ALL_PROCEEDED));
        ProcessInstance<String> instance = new DefaultProcessInstance<>(definition);

        instance.execute(null);
    }

}
