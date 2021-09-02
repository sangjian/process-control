package cn.ideabuffer.process.core.test;

import cn.ideabuffer.process.core.ProcessDefinition;
import cn.ideabuffer.process.core.ProcessDefinitionBuilder;
import cn.ideabuffer.process.core.ProcessInstance;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.nodes.Nodes;
import cn.ideabuffer.process.core.nodes.ParallelBranchNode;
import cn.ideabuffer.process.core.nodes.builder.ParallelBranchNodeBuilder;
import cn.ideabuffer.process.core.nodes.builder.ProcessNodeBuilder;
import cn.ideabuffer.process.core.status.ProcessStatus;
import cn.ideabuffer.process.core.strategies.ProceedStrategies;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

/**
 * @author sangjian.sj
 * @date 2020/03/10
 */
public class ParallelTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParallelTest.class);

    /**
     * 测试并行节点，判断节点运行时线程
     *
     * @throws Exception
     */
    @Test
    public void testParallelNode() throws Exception {
//        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        Thread mainThread = Thread.currentThread();
        ParallelBranchNode node = ParallelBranchNodeBuilder.newBuilder()
            .addBranch(Nodes.newProcessNode(context -> {
                LOGGER.info("in node1");
                assertNotEquals(mainThread, Thread.currentThread());
                return null;
            }))
            .addBranch(Nodes.newProcessNode(context -> {
                LOGGER.info("in node2");
                assertNotEquals(mainThread, Thread.currentThread());
                return null;
            }))
            // 所有分支返回继续，才可进行后续节点执行，否则后续节点不执行
            .proceedWhen(ProceedStrategies.ALL_PROCEEDED).build();
        ProcessDefinition<String> definition = ProcessDefinitionBuilder.<String>newBuilder()
            .addProcessNodes(node)
            .addProcessNodes(ProcessNodeBuilder.newBuilder()
                .by(context -> {
                    LOGGER.info("in node3");
                    assertEquals(mainThread, Thread.currentThread());
                    return null;
                })
                .build()
            )
            .build();
        ProcessInstance<String> instance = definition.newInstance();

        instance.execute(Contexts.newContext());
    }

    /**
     * 测试ALL_PROCEEDED
     *
     * @throws Exception
     */
    @Test
    public void testAllProceeded() throws Exception {
//        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        AtomicBoolean node1Flag = new AtomicBoolean();
        AtomicBoolean node2Flag = new AtomicBoolean();
        AtomicBoolean node3Flag = new AtomicBoolean();
        ParallelBranchNode node = ParallelBranchNodeBuilder.newBuilder()
            .addBranch(Nodes.newProcessNode(context -> {
                LOGGER.info("in node1");
                node1Flag.set(true);
                return null;
            }))
            .addBranch(Nodes.newProcessNode(context -> {
                Thread.sleep(4000);
                LOGGER.info("in node1");
                node2Flag.set(true);
                return null;
            }))
            // 所有分支返回继续，才可进行后续节点执行，否则后续节点不执行
            .proceedWhen(ProceedStrategies.ALL_PROCEEDED)
            .build();
        ProcessDefinition<String> definition = ProcessDefinitionBuilder.<String>newBuilder()
            .addProcessNodes(node)
            .addProcessNodes(ProcessNodeBuilder.newBuilder()
                .by(context -> {
                    LOGGER.info("in node3");
                    // 检查并行执行的两个节点，确保都已执行完毕
                    assertTrue(node1Flag.get() && node2Flag.get());
                    node3Flag.set(true);
                    return null;
                })
                .build()
            )
            .build();
        ProcessInstance<String> instance = definition.newInstance();

        instance.execute(Contexts.newContext());

        assertTrue(node3Flag.get());
    }

    /**
     * 测试AT_LEAST_ONE_PROCEEDED
     * @throws Exception
     */
    @Test
    public void testAtLeastOneProceeded() throws Exception {
//        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        AtomicBoolean node1Flag = new AtomicBoolean();
        AtomicBoolean node2Flag = new AtomicBoolean();
        ParallelBranchNode node = ParallelBranchNodeBuilder.newBuilder()
            .addBranch(Nodes.newProcessNode(context -> {
                LOGGER.info("in node1");
                node1Flag.set(true);
                return ProcessStatus.proceed();
            }))
            .addBranch(Nodes.newProcessNode(context -> {
                Thread.sleep(1000);
                LOGGER.info("in node1");
                node2Flag.set(true);
                return ProcessStatus.complete();
            }))
            // 所有分支返回继续，才可进行后续节点执行，否则后续节点不执行
            .proceedWhen(ProceedStrategies.AT_LEAST_ONE_PROCEEDED)
            .build();
        ProcessDefinition<String> definition = ProcessDefinitionBuilder.<String>newBuilder()
            .addProcessNodes(node)
            .addProcessNodes(ProcessNodeBuilder.newBuilder()
                .by(context -> {
                    LOGGER.info("in node3");
                    // 至少有一个分支返回继续，才可进行后续节点执行，否则后续节点不执行
                    // 判断node1执行完毕，但node2还未执行完毕
                    assertTrue(node1Flag.get() && !node2Flag.get());
                    return null;
                })
                .build()
            )
            .build();
        ProcessInstance<String> instance = definition.newInstance();

        instance.execute(Contexts.newContext());

    }

    /**
     * 测试AT_LEAST_ONE_PROCEEDED
     * @throws Exception
     */
    @Test
    public void testAtLeastOneProceeded2() throws Exception {
//        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        AtomicBoolean node1Flag = new AtomicBoolean();
        AtomicBoolean node2Flag = new AtomicBoolean();
        AtomicBoolean node3Flag = new AtomicBoolean();
        ParallelBranchNode node = ParallelBranchNodeBuilder.newBuilder()
            .addBranch(Nodes.newProcessNode(context -> {
                LOGGER.info("in node1");
                node1Flag.set(true);
                return ProcessStatus.complete();
            }))
            .addBranch(Nodes.newProcessNode(context -> {
                Thread.sleep(1000);
                LOGGER.info("in node2");
                node2Flag.set(true);
                return ProcessStatus.complete();
            }))
            // 至少有一个分支返回继续，才可进行后续节点执行，否则后续节点不执行
            .proceedWhen(ProceedStrategies.AT_LEAST_ONE_PROCEEDED)
            .build();
        ProcessDefinition<String> definition = ProcessDefinitionBuilder.<String>newBuilder()
            .addProcessNodes(node)
            .addProcessNodes(ProcessNodeBuilder.newBuilder()
                .by(context -> {
                    LOGGER.info("in node3");
                    node3Flag.set(true);
                    return null;
                })
                .build()
            )
            .build();
        ProcessInstance<String> instance = definition.newInstance();

        instance.execute(Contexts.newContext());

        // 前两个节点都返回了complete，node3不会执行
        assertFalse(node3Flag.get());
    }

    /**
     * 测试AT_LEAST_ONE_FINISHED
     * @throws Exception
     */
    @Test
    public void testAtLeastOneFinished() throws Exception {
//        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        AtomicBoolean node1Flag = new AtomicBoolean();
        AtomicBoolean node2Flag = new AtomicBoolean();
        AtomicBoolean node3Flag = new AtomicBoolean();
        ParallelBranchNode node = ParallelBranchNodeBuilder.newBuilder()
            .addBranch(Nodes.newProcessNode(context -> {
                LOGGER.info("in node1");
                node1Flag.set(true);
                return ProcessStatus.complete();
            }))
            .addBranch(Nodes.newProcessNode(context -> {
                Thread.sleep(1000);
                LOGGER.info("in node1");
                node2Flag.set(true);
                return ProcessStatus.complete();
            }))
            // 至少有一个节点执行完毕，才可进行后续节点执行，否则后续节点不执行
            // 执行完毕不关心是proceed还是complete
            .proceedWhen(ProceedStrategies.AT_LEAST_ONE_FINISHED)
            .build();
        ProcessDefinition<String> definition = ProcessDefinitionBuilder.<String>newBuilder()
            .addProcessNodes(node)
            .addProcessNodes(ProcessNodeBuilder.newBuilder()
                .by(context -> {
                    LOGGER.info("in node3");
                    node3Flag.set(true);
                    // 判断node1执行完毕，但node2还未执行完毕
                    assertTrue(node1Flag.get() && !node2Flag.get());
                    return null;
                })
                .build()
            )
            .build();
        ProcessInstance<String> instance = definition.newInstance();

        instance.execute(Contexts.newContext());

        // 前两个节点都返回了complete，node3会执行
        assertTrue(node3Flag.get());
    }

    /**
     * 测试ALL_FINISHED
     * @throws Exception
     */
    @Test
    public void testAllFinished() throws Exception {
//        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        AtomicBoolean node1Flag = new AtomicBoolean();
        AtomicBoolean node2Flag = new AtomicBoolean();
        AtomicBoolean node3Flag = new AtomicBoolean();
        ParallelBranchNode node = ParallelBranchNodeBuilder.newBuilder()
            .addBranch(Nodes.newProcessNode(context -> {
                LOGGER.info("in node1");
                node1Flag.set(true);
                return ProcessStatus.complete();
            }))
            .addBranch(Nodes.newProcessNode(context -> {
                Thread.sleep(1000);
                LOGGER.info("in node2");
                node2Flag.set(true);
                return ProcessStatus.complete();
            }))
            // 所有分支返回继续，才可进行后续节点执行，否则后续节点不执行
            .proceedWhen(ProceedStrategies.ALL_FINISHED)
            .build();
        ProcessDefinition<String> definition = ProcessDefinitionBuilder.<String>newBuilder()
            .addProcessNodes(node)
            .addProcessNodes(ProcessNodeBuilder.newBuilder()
                .by(context -> {
                    LOGGER.info("in node3");
                    node3Flag.set(true);
                    // 检查并行执行的两个节点，判断所有节点并行执行结束
                    assertTrue(node1Flag.get() && node2Flag.get());
                    return null;
                })
                .build()
            )
            .build();
        ProcessInstance<String> instance = definition.newInstance();

        instance.execute(Contexts.newContext());

        assertTrue(node3Flag.get());
    }

    /**
     * 测试SUBMITTED
     * @throws Exception
     */
    @Test
    public void testSubmitted() throws Exception {
//        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        AtomicBoolean node1Flag = new AtomicBoolean();
        AtomicBoolean node2Flag = new AtomicBoolean();
        AtomicBoolean node3Flag = new AtomicBoolean();
        ParallelBranchNode node = ParallelBranchNodeBuilder.newBuilder()
            .addBranch(Nodes.newProcessNode(context -> {
                Thread.sleep(1000);
                LOGGER.info("in node1");
                node1Flag.set(true);
                return ProcessStatus.complete();
            }))
            .addBranch(Nodes.newProcessNode(context -> {
                Thread.sleep(1000);
                LOGGER.info("in node2");
                node2Flag.set(true);
                return ProcessStatus.complete();
            }))
            // 所有分支返回继续，才可进行后续节点执行，否则后续节点不执行
            .proceedWhen(ProceedStrategies.SUBMITTED)
            .build();
        ProcessDefinition<String> definition = ProcessDefinitionBuilder.<String>newBuilder()
            .addProcessNodes(node)
            .addProcessNodes(ProcessNodeBuilder.newBuilder()
                .by(context -> {
                    LOGGER.info("in node3");
                    node3Flag.set(true);
                    // 检查并行执行的两个节点，只关心节点已提交至并行执行，不关心执行进度
                    assertTrue(!node1Flag.get() && !node2Flag.get());
                    return null;
                })
                .build()
            )
            .build();
        ProcessInstance<String> instance = definition.newInstance();

        instance.execute(Contexts.newContext());

        assertTrue(node3Flag.get());
    }

}
