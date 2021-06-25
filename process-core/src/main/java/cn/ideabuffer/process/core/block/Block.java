package cn.ideabuffer.process.core.block;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.context.KeyMapper;
import cn.ideabuffer.process.core.context.Parameter;
import cn.ideabuffer.process.core.nodes.condition.DoWhileConditionNode;
import cn.ideabuffer.process.core.nodes.condition.WhileConditionNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

/**
 * <ul>
 * <li>用于表示当前节点所在的范围，通过Context获取</li>
 * <li>同一block内，数据可共享，与当前context数据隔离</li>
 * <li>同时，如果block在{@link WhileConditionNode}或{@link DoWhileConditionNode}节点中，可以执行break和continue操作</li>
 * </ul>
 *
 * @author sangjian.sj
 * @date 2020/02/22
 * @see Context#getBlock()
 */
public interface Block {

    /**
     * 是否允许break，改方法先判断当前block是否支持break，如果当前block不支持，向上遍历block。
     *
     * @return 允许break，返回true，否则返回false
     */
    boolean allowBreak();

    /**
     * 是否允许continue，改方法先判断当前block是否支持continue，如果当前block不支持，向上遍历block。
     *
     * @return 允许continue，返回true，否则返回false
     */
    boolean allowContinue();

    /**
     * 执行break操作。
     */
    void doBreak();

    /**
     * 执行continue操作。
     */
    void doContinue();

    /**
     * 是否已执行break操作。
     *
     * @return 如果已执行break操作，返回true，否则返回false
     */
    boolean hasBroken();

    /**
     * 是否已执行continue操作。
     *
     * @return 如果已执行continue操作，返回true，否则返回false
     */
    boolean hasContinued();

    /**
     * 获取parent。
     *
     * @return parent
     */
    @Nullable
    Block getParent();

}
