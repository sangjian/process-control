package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.Executable;
import cn.ideabuffer.process.core.Matchable;
import cn.ideabuffer.process.core.Node;
import cn.ideabuffer.process.core.Parallelizable;
import cn.ideabuffer.process.core.rule.Rule;

import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/01/19
 */
public interface ExecutableNode extends Node, Executable, Parallelizable, Matchable {

    @Override
    ExecutableNode processOn(Rule rule);

    @Override
    ExecutableNode parallel();

    @Override
    ExecutableNode parallel(Executor executor);
}
