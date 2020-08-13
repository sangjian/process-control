package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.*;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.context.KeyMapper;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

/**
 * @author sangjian.sj
 * @date 2020/01/19
 */
public interface ExecutableNode<R, P extends Processor<R>> extends Node, Executable, Parallelizable, Matchable {

    boolean isParallel();

    void registerProcessor(P processor);

    void addProcessListeners(@NotNull ProcessListener<R>... listeners);

    P getProcessor();

    List<ProcessListener<R>> getListeners();

    KeyMapper getKeyMapper();

    void setKeyMapper(KeyMapper mapper);

    void setResultKey(Key<R> resultKey);

    Key<R> getResultKey();

    void returnOn(ReturnCondition<R> condition);

    ReturnCondition<R> getReturnCondition();

    void setRequiredKeys(Set<Key<?>> keys);

    Set<Key<?>> getRequiredKeys();
}
