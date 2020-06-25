package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.*;
import cn.ideabuffer.process.core.context.KeyMapper;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/01/19
 */
public interface ExecutableNode<R, P extends Processor<R>> extends Node, Executable, Parallelizable, Matchable {

    boolean isParallel();

    void registerNodeListener(NodeListener<R> listener);

    void registerProcessor(P processor);

    void addProcessListeners(@NotNull ProcessListener<R>... listeners);

    NodeListener<R> getNodeListener();

    P getProcessor();

    List<ProcessListener<R>> getListeners();

    KeyMapper getKeyMapper();

    void setKeyMapper(KeyMapper mapper);
}
