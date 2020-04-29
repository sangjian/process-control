package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/01/19
 */
public interface ExecutableNode<R> extends Node, Executable, Parallelizable, Matchable, NodeListener<R> {

    void addListeners(@NotNull ProcessListener... listener);

    List<ProcessListener> getListeners();
}
