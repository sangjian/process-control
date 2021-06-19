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

    Key<R> getResultKey();

    void setResultKey(Key<R> resultKey);

    void returnOn(ReturnCondition<R> condition);

    ReturnCondition<R> getReturnCondition();

    Set<Key<?>> getReadableKeys();

    void setReadableKeys(Set<Key<?>> keys);

    Set<Key<?>> getWritableKeys();

    void setWritableKeys(Set<Key<?>> keys);
}
