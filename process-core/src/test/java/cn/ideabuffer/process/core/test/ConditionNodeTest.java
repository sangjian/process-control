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
import cn.ideabuffer.process.core.nodes.builder.ProcessNodeBuilder;
import cn.ideabuffer.process.core.rules.Rule;
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
        BranchNode trueBranch = Nodes.newBranch(ProcessNodeBuilder.<ProcessStatus>newBuilder()
            .by(context -> {
                context.put(key, 1);
                return ProcessStatus.proceed();
            })
            .writableKeys(key)
            .build());
        // 创建false分支
        BranchNode falseBranch = Nodes.newBranch(ProcessNodeBuilder.<ProcessStatus>newBuilder()
            .by(context -> {
                context.put(key, 2);
                return ProcessStatus.proceed();
            })
            .writableKeys(key)
            .build());

        // 判断条件，判断key对应的值是否小于5
        Rule rule = ctx -> ctx.get(key, 0) < 5;

        // 如果满足条件，执行true分支，否则执行false分支
        definition.addIf(
            Nodes.newIf(rule, key)
                .then(trueBranch)
                .otherwise(falseBranch)
        );
        ProcessInstance<String> instance = definition.newInstance();
        Context context = Contexts.newContext();
        context.put(key, 0);

        instance.execute(context);
        // 结果为1，说明走了true分支
        assertEquals(1, (int)context.get(key));
    }

    @Test
    public void testWhile() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        AtomicInteger counter1 = new AtomicInteger();
        AtomicInteger counter2 = new AtomicInteger();
        AtomicInteger ruleCounter = new AtomicInteger();

        Key<Integer> key = Contexts.newKey("k", int.class);
        /**
         * 每次循环判断k<10
         * while(k < 10) {
         *      ...
         * }
         */
        Rule rule = context -> {
            int k = context.get(key, 0);
            // 规则执行次数
            ruleCounter.incrementAndGet();
            return k < 10;
        };
        definition.addWhile(Nodes.newWhile(rule, key)
            .then(
                ProcessNodeBuilder.<ProcessStatus>newBuilder()
                    .by(context -> {
                        // 节点执行次数
                        counter1.incrementAndGet();
                        int k = context.get(key, 0);
                        // 每次执行++k
                        context.put(key, ++k);
                        return ProcessStatus.proceed();
                    })
                    .readableKeys(key)
                    .writableKeys(key)
                    .build(),
                ProcessNodeBuilder.<ProcessStatus>newBuilder()
                    .by(context -> {
                        // 节点执行次数
                        counter2.incrementAndGet();
                        int k = context.get(key, 0);
                        // 每次执行++k
                        context.put(key, ++k);
                        return ProcessStatus.proceed();
                    })
                    .readableKeys(key)
                    .writableKeys(key)
                    .build()
            )
        );
        ProcessInstance<String> instance = definition.newInstance();
        Context context = Contexts.newContext();
        context.put(key, 0);
        instance.execute(context);
        // 整体应该循环5次，规则需执行6次
        assertEquals(5, counter1.get());
        assertEquals(5, counter2.get());
        assertEquals(6, ruleCounter.get());
    }

    @Test
    public void testDoWhile() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        AtomicInteger counter1 = new AtomicInteger();
        AtomicInteger counter2 = new AtomicInteger();
        AtomicInteger ruleCounter = new AtomicInteger();

        Key<Integer> key = Contexts.newKey("k", int.class);
        /**
         * 每次循环判断k<10
         * do {
         *      ...
         * } while(k < 10)
         */
        Rule rule = context -> {
            int k = context.get(key, 0);
            // 规则执行次数
            ruleCounter.incrementAndGet();
            return k < 10;
        };
        definition.addDoWhile(Nodes.newDoWhile(rule, key)
            .then(
                ProcessNodeBuilder.<ProcessStatus>newBuilder()
                    .by(context -> {
                        // 节点执行次数
                        counter1.incrementAndGet();
                        int k = context.get(key, 0);
                        // 每次执行++k
                        context.put(key, ++k);
                        return ProcessStatus.proceed();
                    })
                    .readableKeys(key)
                    .writableKeys(key)
                    .build(),
                ProcessNodeBuilder.<ProcessStatus>newBuilder()
                    .by(context -> {
                        // 节点执行次数
                        counter2.incrementAndGet();
                        int k = context.get(key, 0);
                        // 每次执行++k
                        context.put(key, ++k);
                        return ProcessStatus.proceed();
                    })
                    .readableKeys(key)
                    .writableKeys(key)
                    .build()
            )
        );
        ProcessInstance<String> instance = definition.newInstance();
        Context context = Contexts.newContext();
        context.put(key, 0);
        instance.execute(context);
        // 整体应该循环5次，规则需执行5次
        assertEquals(5, counter1.get());
        assertEquals(5, counter2.get());
        assertEquals(5, ruleCounter.get());
    }

    /**
     * <pre>{@code
     *
     * // k < 10 并且总执行次数 < 10
     * while(k < 10 && counter.getAndIncrement() < 10) {
     *      // node1
     *      {
     *          // node1执行次数
     *          processor1Counter.incrementAndGet();
     *          ++k;
     *          if (k == 5) {
     *              // continue次数
     *              continueCounter.getAndIncrement();
     *              continue;
     *         }
     *      }
     *      // node2
     *      {
     *          // node2执行次数
     *          processor2Counter.incrementAndGet();
     *          if (k > processor2MaxK.get()) {
     *              // node2执行最大的k
     *              processor2MaxK.set(k);
     *          }
     *      }
     * }
     * }
     * </pre>
     *
     */
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

        Rule rule = context -> {
            LOGGER.info("in rule k={}, counter:{}", context.get(key, 0), counter.get());
            return context.get(key, 0) < 10 && counter.getAndIncrement() < 10;
        };
        definition.addWhile(Nodes.newWhile(rule, key)
            .then(
                ProcessNodeBuilder.<ProcessStatus>newBuilder()
                    .by(context -> {
                        processor1Counter.incrementAndGet();
                        Block block = context.getBlock();
                        int k = context.get(key, 0);
                        LOGGER.info("in node1 k={}", k);
                        context.put(key, ++k);
                        if (k == 5 && block.allowContinue()) {
                            LOGGER.info("in node1 k == 5, continued");
                            continueCounter.getAndIncrement();
                            context.put(key, 0);
                            block.doContinue();
                        }
                        return ProcessStatus.proceed();
                    })
                    .readableKeys(key)
                    .writableKeys(key)
                    .build(),
                ProcessNodeBuilder.<ProcessStatus>newBuilder()
                    .by(context -> {
                        processor2Counter.incrementAndGet();
                        int k = context.get(key, 0);
                        if (k > processor2MaxK.get()) {
                            processor2MaxK.set(k);
                        }
                        LOGGER.info("in node2 k={}, processor2MaxK:{}", k, processor2MaxK);
                        return ProcessStatus.proceed();
                    })
                    .readableKeys(key)
                    .writableKeys(key)
                    .build()
            )
        );
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

    /**
     * <pre>{@code
     *
     * do {
     *      // node1
     *      {
     *          processor1Counter.incrementAndGet();
     *          ++k;
     *          if (k == 5) {
     *              continueCounter.getAndIncrement();
     *              continue;
     *         }
     *      }
     *      // node2
     *      {
     *          processor2Counter.incrementAndGet();
     *          if (k > processor2MaxK.get()) {
     *              processor2MaxK.set(k);
     *          }
     *      }
     * } while(k < 10 && counter.getAndIncrement() < 10)
     * }
     * </pre>
     *
     */
    @Test
    public void testDoWhileContinue() throws Exception {
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

        Rule rule = context -> {
            LOGGER.info("in rule k={}, counter:{}", context.get(key, 0), counter.get());
            return context.get(key, 0) < 10 && counter.getAndIncrement() < 10;
        };
        definition.addDoWhile(Nodes.newDoWhile(rule, key)
            .then(
                ProcessNodeBuilder.<ProcessStatus>newBuilder()
                    .by(context -> {
                        processor1Counter.incrementAndGet();
                        Block block = context.getBlock();
                        int k = context.get(key, 0);
                        LOGGER.info("in node1 k={}", k);
                        context.put(key, ++k);
                        if (k == 5 && block.allowContinue()) {
                            LOGGER.info("in node1 k == 5, continued");
                            continueCounter.getAndIncrement();
                            context.put(key, 0);
                            block.doContinue();
                        }
                        return ProcessStatus.proceed();
                    })
                    .readableKeys(key)
                    .writableKeys(key)
                    .build(),
                ProcessNodeBuilder.<ProcessStatus>newBuilder()
                    .by(context -> {
                        processor2Counter.incrementAndGet();
                        int k = context.get(key, 0);
                        if (k > processor2MaxK.get()) {
                            processor2MaxK.set(k);
                        }
                        LOGGER.info("in node2 k={}, processor2MaxK:{}", k, processor2MaxK);
                        return ProcessStatus.proceed();
                    })
                    .readableKeys(key)
                    .writableKeys(key)
                    .build()
            )
        );
        ProcessInstance<String> instance = definition.newInstance();

        Context context = Contexts.newContext();

        instance.execute(context);

        assertEquals(2, continueCounter.get());
        // k为5的时候进行continue，所以processor2中的k不可能大于4
        assertEquals(4, processor2MaxK.get());
        // processor1节点每次都会执行
        assertEquals(11, processor1Counter.get());
        // 两次k为5，所以只有9次进到processor2
        assertEquals(9, processor2Counter.get());
    }

    /**
     * <pre>
     *     {@code
     *     while(k < 10) {
     *          // node1
     *          {
     *              // node1执行标记
     *              processor1Flag.set(true);
     *              k++;
     *          }
     *          // node2
     *          {
     *              // node2执行标记
     *              processor2Flag.set(true);
     *              k++;
     *              break;
     *          }
     *          // node3
     *          {
     *              // node3执行标记
     *              processor3Flag.set(true);
     *          }
     *
     *     }
     *
     *     }
     * </pre>
     * @throws Exception
     */
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
            .then(ProcessNodeBuilder.<ProcessStatus>newBuilder().by(context -> {
                    processor1Flag.set(true);
                    context.put(key, context.get(key, 0) + 1);
                    return ProcessStatus.proceed();
                }).readableKeys(key).writableKeys(key).build(),
                ProcessNodeBuilder.<ProcessStatus>newBuilder().by(context -> {
                    processor2Flag.set(true);
                    context.put(key, context.get(key, 0) + 1);
                    if (context.getBlock().allowBreak()) {
                        breakFlag.set(true);
                        context.getBlock().doBreak();
                    }
                    return ProcessStatus.proceed();
                }).readableKeys(key).writableKeys(key).build(),
                ProcessNodeBuilder.<ProcessStatus>newBuilder().by(context -> {
                    processor3Flag.set(true);
                    return ProcessStatus.proceed();
                }).build()));
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
     *     {@code
     *     do {
     *          // node1
     *          {
     *              processor1Flag.set(true);
     *              k++;
     *          }
     *          // node2
     *          {
     *              processor2Flag.set(true);
     *              k++;
     *              break;
     *          }
     *          // node3
     *          {
     *              processor3Flag.set(true);
     *          }
     *
     *     } while(k < 10)
     *
     *     }
     * </pre>
     * @throws Exception
     */
    @Test
    public void testDoWhileBreak() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        Key<Integer> key = Contexts.newKey("k", int.class);
        Rule rule = context -> context.get(key, 0) < 10;
        AtomicBoolean processor1Flag = new AtomicBoolean();
        AtomicBoolean processor2Flag = new AtomicBoolean();
        AtomicBoolean processor3Flag = new AtomicBoolean();
        AtomicBoolean breakFlag = new AtomicBoolean();

        // 循环分支包含3个节点，前两个节点每次对k进行加1，第二个节点进行break
        definition.addDoWhile(Nodes.newDoWhile(rule, key)
            .then(ProcessNodeBuilder.<ProcessStatus>newBuilder().by(context -> {
                    processor1Flag.set(true);
                    context.put(key, context.get(key, 0) + 1);
                    return ProcessStatus.proceed();
                }).readableKeys(key).writableKeys(key).build(),
                ProcessNodeBuilder.<ProcessStatus>newBuilder().by(context -> {
                    processor2Flag.set(true);
                    context.put(key, context.get(key, 0) + 1);
                    if (context.getBlock().allowBreak()) {
                        breakFlag.set(true);
                        context.getBlock().doBreak();
                    }
                    return ProcessStatus.proceed();
                }).readableKeys(key).writableKeys(key).build(),
                ProcessNodeBuilder.<ProcessStatus>newBuilder().by(context -> {
                    processor3Flag.set(true);
                    return ProcessStatus.proceed();
                }).build()));
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
     *     {@code
     *     int k = 0;
     *        int counter = 0;
     *        while (true) {
     *            while (k < 10) {
     *                k++;
     *                if (k % 2 == 1) {
     *                    if (k == 5) { continue; }
     *                    System.out.println("after if continue");
     *                }
     *                if (k % 2 == 0) {
     *                    if (k == 8) { break; }
     *                    System.out.println("after if break");
     *                }
     *                counter++;
     *            }
     *            LOGGER.info("k = {}, counter = {}", k, counter);
     *            break;
     *        }
     *
     *     }
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
                    ProcessNodeBuilder.<Void>newBuilder().by(context -> {
                        context.put(key, context.get(key, 0) + 1);
                        return null;
                    }).readableKeys(key).writableKeys(key).build(),
                    Nodes.newIf(context -> context.get(key, 0) % 2 == 1, key).then(
                        Nodes.newIf(context -> context.get(key, 0) == 5, key)
                            .then(
                                ProcessNodeBuilder.<Void>newBuilder().by(context -> {
                                    if (context.getBlock().allowContinue()) {
                                        context.getBlock().doContinue();
                                    }
                                    return null;
                                }).build(),
                                ProcessNodeBuilder.<Void>newBuilder().by(context -> {
                                    afterContinueFlag.set(true);
                                    System.out.println("after if continue");
                                    return null;
                                }).build()).end()
                    ).end(),
                    Nodes.newIf(context -> context.get(key, 0) % 2 == 0, key).then(
                        Nodes.newIf(context -> context.get(key, 0) == 8, key).then(
                            ProcessNodeBuilder.<Void>newBuilder().by(context -> {
                                if (context.getBlock().allowBreak()) {
                                    context.getBlock().doBreak();
                                }
                                return null;
                            }).build(),
                            ProcessNodeBuilder.<Void>newBuilder().by(context -> {
                                afterBreakFlag.set(true);
                                System.out.println("after if break");
                                return null;
                            }).build()).end()
                    ).end(),
                    ProcessNodeBuilder.<Void>newBuilder().by(context -> {
                        context.put(counter, context.get(counter, 0) + 1);
                        return null;
                    }).readableKeys(counter).writableKeys(counter).build()),
            ProcessNodeBuilder.<Void>newBuilder().by(context -> {
                LOGGER.info("k = {}, counter = {}", context.get(key), context.get(counter));
                return null;
            }).readableKeys(key, counter).build(),
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
