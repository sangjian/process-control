package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.*;
import cn.ideabuffer.process.core.context.Key;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/01/19
 */
public interface ExecutableNode<R, P extends Processor<R>> extends Node, Executable, Parallelizable, Matchable, KeyManager {

    boolean isParallel();

    void registerProcessor(@NotNull P processor);

    void addProcessListeners(@NotNull ProcessListener<R>... listeners);

    P getProcessor();

    List<ProcessListener<R>> getListeners();

    Key<R> getResultKey();

    void setResultKey(Key<R> resultKey);

    void returnOn(ReturnCondition<R> condition);

    ReturnCondition<R> getReturnCondition();
}
