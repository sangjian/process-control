package cn.ideabuffer.process.core;

import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.processors.wrapper.StatusWrapperHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

/**
 * @author sangjian.sj
 * @date 2020/03/24
 */
public interface ProcessDefinition<R> extends Lifecycle {

    /**
     * 获取所有节点
     *
     * @return
     */
    @NotNull
    Node[] getNodes();

    InitializeMode getInitializeMode();

    @NotNull
    ProcessInstance<R> newInstance();

    @Nullable
    Key<R> getResultKey();

    ReturnCondition<R> getReturnCondition();

    /**
     * 返回包装处理器
     *
     * @return 包装处理器列表
     * @see java.util.Collections#unmodifiableList(List)
     */
    @NotNull
    List<StatusWrapperHandler> getHandlers();

    boolean isDeclaredRestrict();

    Set<Key<?>> getDeclaredKeys();

}
