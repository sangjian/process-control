package cn.ideabuffer.process;

import cn.ideabuffer.process.nodes.Nodes;
import cn.ideabuffer.process.nodes.ParallelBranchNode;
import cn.ideabuffer.process.nodes.parallel.TestParallelNode1;
import cn.ideabuffer.process.nodes.parallel.TestParallelNode2;
import cn.ideabuffer.process.strategy.ProceedStrategies;
import org.junit.Test;

/**
 * @author sangjian.sj
 * @date 2020/03/10
 */
public class ParallelTest {

    @Test
    public void testParallelNode() throws Exception {
        Chain chain = new DefaultChain();
        ParallelBranchNode node = Nodes.newParallelBranchNode();
        chain.addProcessNode(node.addBranch(new TestParallelNode1()).addBranch(new TestParallelNode2())
            .proceedWhen(ProceedStrategies.ALL_PROCEEDED));
        chain.execute(null);
    }

}
