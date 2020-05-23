package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.Node;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.processors.ResultProcessor;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

/**
 * 基础节点，在整个流程最后执行，用于计算结果并返回
 *
 * @author sangjian.sj
 * @date 2020/03/24
 */
public interface BaseNode<R> extends Node {

    /**
     * 执行并返回结果
     *
     * @param context 流程上下文
     * @param status  流程执行状态
     * @return 流程处理结果
     */
    R invoke(@NotNull Context context, @NotNull ProcessStatus status);

    void setProcessor(ResultProcessor<R> processor);

    ResultProcessor<R> getProcessor();

}
