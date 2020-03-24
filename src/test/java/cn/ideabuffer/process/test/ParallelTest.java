package cn.ideabuffer.process.test;

import cn.ideabuffer.process.DefaultProcessDefine;
import cn.ideabuffer.process.DefaultProcessInstance;
import cn.ideabuffer.process.ProcessDefine;
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
        ProcessDefine<String> define = new DefaultProcessDefine<>();
        ParallelBranchNode node = Nodes.newParallelBranchNode();
        define.addProcessNode(node.addBranch(new TestParallelNode1()).addBranch(new TestParallelNode2())
            .proceedWhen(ProceedStrategies.ALL_PROCEEDED));
        ProcessInstance<String> instance = new DefaultProcessInstance<>(define);

        instance.execute(null);
    }

}
