package cn.ideabuffer.process.core.test;

import cn.ideabuffer.process.core.DefaultProcessDefinition;
import cn.ideabuffer.process.core.ProcessDefinition;
import cn.ideabuffer.process.core.ProcessInstance;
import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.block.Block;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.nodes.Nodes;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.rule.Rule;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

/**
 * @author sangjian.sj
 * @date 2020/06/02
 */
public class ConditionNodeTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessInstanceTest.class);

    @Test
    public void testIf() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        Key<Integer> key = Contexts.newKey("k", int.class);
        // 创建true分支
        BranchNode trueBranch = Nodes.newBranch(Nodes.newProcessNode(context -> {
            context.put(key, 1);
            return ProcessStatus.proceed();
        }, key));
        // 创建false分支
        BranchNode falseBranch = Nodes.newBranch(Nodes.newProcessNode(context -> {
            context.put(key, 2);
            return ProcessStatus.proceed();
        }, key));

        // 判断条件，判断key对应的值是否小于5
        Rule rule = ctx -> ctx.get(key, 0) < 5;

        // 如果满足条件，执行true分支，否则执行false分支
        definition.addIf(Nodes.newIf(rule, key).then(trueBranch)
            .otherwise(falseBranch));
        ProcessInstance<String> instance = definition.newInstance();
        Context context = Contexts.newContext();
        // 设置key值为1
        context.put(key, 0);

        instance.execute(context);
        assertEquals(1, (int)context.get(key));
    }

    @Test
    public void testWhile() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        AtomicInteger counter1 = new AtomicInteger();
        AtomicInteger counter2 = new AtomicInteger();
        AtomicInteger ruleCounter = new AtomicInteger();

        Key<Integer> key = Contexts.newKey("k", int.class);
        Rule rule = context -> {
            int k = context.getBlock().get(key, 0);
            ruleCounter.incrementAndGet();
            return k < 10;
        };
        definition.addWhile(Nodes.newWhile(rule, key)
            .then(Nodes.newProcessNode(context -> {
                    counter1.incrementAndGet();
                    Block block = context.getBlock();
                    int k = block.get(key, 0);
                    block.put(key, ++k);
                    return ProcessStatus.proceed();
                }, key),
                Nodes.newProcessNode(context -> {
                    counter2.incrementAndGet();
                    Block block = context.getBlock();
                    int k = block.get(key, 0);
                    block.put(key, ++k);
                    return ProcessStatus.proceed();
                }, key)));
        ProcessInstance<String> instance = definition.newInstance();
        Context context = Contexts.newContext();
        context.put(key, 0);
        instance.execute(context);
        assertEquals(5, counter1.get());
        assertEquals(5, counter2.get());
        assertEquals(6, ruleCounter.get());
    }

    @Test
    public void testWhileContinue() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        Key<Integer> key = Contexts.newKey("k", int.class);
        // 整体循环计数器
        AtomicInteger counter = new AtomicInteger();
        // continue操作次数
        AtomicInteger continueCounter = new AtomicInteger();
        // processor1中的执行计数
        AtomicInteger processor1Counter = new AtomicInteger();
        // processor2中的执行计数
        AtomicInteger processor2Counter = new AtomicInteger();
        // processor2中的k的最大值
        AtomicInteger processor2MaxK = new AtomicInteger();
        Rule rule = context -> context.getBlock().get(key, 0) < 10 && counter.getAndIncrement() < 10;
        definition.addWhile(Nodes.newWhile(rule)
            .then(Nodes.newProcessNode(context -> {
                    processor1Counter.incrementAndGet();
                    Block block = context.getBlock();
                    int k = block.get(key, 0);
                    block.put(key, ++k);
                    if (k == 5 && block.allowContinue()) {
                        continueCounter.getAndIncrement();
                        block.put(key, 0);
                        block.doContinue();
                    }
                    return ProcessStatus.proceed();
                }, key),
                Nodes.newProcessNode(context -> {
                    processor2Counter.incrementAndGet();
                    Block block = context.getBlock();
                    int k = block.get(key, 0);
                    if (k > processor2MaxK.get()) {
                        processor2MaxK.set(k);
                    }
                    return ProcessStatus.proceed();
                }, key)));
        ProcessInstance<String> instance = definition.newInstance();

        Context context = Contexts.newContext();

        instance.execute(context);

        assertEquals(2, continueCounter.get());
        // k为5的时候进行continue，所以processor2中的k不可能大于4
        assertEquals(4, processor2MaxK.get());
        // processor1节点每次都会执行
        assertEquals(10, processor1Counter.get());
        // 两次k为5，所以只有8次进到processor2
        assertEquals(8, processor2Counter.get());
    }

    @Test
    public void testWhileBreak() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        Key<Integer> key = Contexts.newKey("k", int.class);
        Rule rule = context -> context.get(key, 0) < 10;
        AtomicBoolean processor1Flag = new AtomicBoolean();
        AtomicBoolean processor2Flag = new AtomicBoolean();
        AtomicBoolean processor3Flag = new AtomicBoolean();
        AtomicBoolean breakFlag = new AtomicBoolean();

        // 循环分支包含3个节点，前两个节点每次对k进行加1，第二个节点进行break
        definition.addWhile(Nodes.newWhile(rule, key)
            .then(Nodes.newProcessNode(context -> {
                processor1Flag.set(true);
                context.put(key, context.get(key, 0) + 1);
                return ProcessStatus.proceed();
            }, key), Nodes.newProcessNode(context -> {
                processor2Flag.set(true);
                context.put(key, context.get(key, 0) + 1);
                if (context.getBlock().allowBreak()) {
                    breakFlag.set(true);
                    context.getBlock().doBreak();
                }
                return ProcessStatus.proceed();
            }, key), Nodes.newProcessNode(context -> {
                processor3Flag.set(true);
                return ProcessStatus.proceed();
            }, key)));
        ProcessInstance<String> instance = definition.newInstance();
        Context context = Contexts.newContext();

        instance.execute(context);

        // 执行processor1标志
        assertTrue(processor1Flag.get());
        // 执行processor2标志
        assertTrue(processor2Flag.get());
        // 执行break标志
        assertTrue(breakFlag.get());
        // 执行processor3标志
        assertFalse(processor3Flag.get());
        // 对k的累加结果
        assertEquals(2, (int)context.get(key));

    }

    /**
     * <pre>
     * int k = 0;
     * int counter = 0;
     * while (true) {
     *     while (k < 10) {
     *         k++;
     *         if (k % 2 == 1) {
     *             if (k == 5) { continue; }
     *             System.out.println("after if continue");
     *         }
     *         if (k % 2 == 0) {
     *             if (k == 8) { break; }
     *             System.out.println("after if break");
     *         }
     *         counter++;
     *     }
     *     LOGGER.info("k = {}, counter = {}", k, counter);
     *     break;
     * }
     * </pre>
     *
     * @throws Exception
     */
    @Test
    public void testNesting() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        Key<Integer> key = Contexts.newKey("k", int.class);
        Key<Integer> counter = Contexts.newKey("counter", int.class);
        Rule rule = context -> context.get(key, 0) < 10;

        AtomicBoolean afterContinueFlag = new AtomicBoolean(false);
        AtomicBoolean afterBreakFlag = new AtomicBoolean(false);

        definition.addWhile(Nodes.newWhile((ctx) -> true).then(
            Nodes.newWhile(rule, key)
                .then(
                    Nodes.newProcessNode(context -> {
                        context.put(key, context.get(key, 0) + 1);
                        return null;
                    }, key),
                    Nodes.newIf(context -> context.get(key, 0) % 2 == 1, key).then(
                        Nodes.newIf(context -> context.get(key, 0) == 5, key).then(Nodes.newProcessNode(context -> {
                            if (context.getBlock().allowContinue()) {
                                context.getBlock().doContinue();
                            }
                            return null;
                        }, key), Nodes.newProcessNode(context -> {
                            afterContinueFlag.set(true);
                            System.out.println("after if continue");
                            return null;
                        }, key)).end()
                    ).end(),
                    Nodes.newIf(context -> context.get(key, 0) % 2 == 0, key).then(
                        Nodes.newIf(context -> context.get(key, 0) == 8, key).then(Nodes.newProcessNode(context -> {
                            if (context.getBlock().allowBreak()) {
                                context.getBlock().doBreak();
                            }
                            return null;
                        }, key), Nodes.newProcessNode(context -> {
                            afterBreakFlag.set(true);
                            System.out.println("after if break");
                            return null;
                        }, key)).end()
                    ).end(),
                    Nodes.newProcessNode(context -> {
                        context.put(counter, context.get(counter, 0) + 1);
                        return null;
                    }, counter)),
            Nodes.newProcessNode((Processor<Void>)context -> {
                LOGGER.info("k = {}, counter = {}", context.get(key), context.get(counter));
                return null;
            }, key, counter),
            Nodes.newProcessNode((Processor<Void>)context -> {
                if (context.getBlock().allowBreak()) {
                    context.getBlock().doBreak();
                }
                return null;
            })
        ));
        ProcessInstance<String> instance = definition.newInstance();
        Context context = Contexts.newContext();

        instance.execute(context);
        assertEquals(8, (int)context.get(key));
        assertEquals(6, (int)context.get(counter));

        assertFalse(afterContinueFlag.get());
        assertFalse(afterBreakFlag.get());
    }

}
