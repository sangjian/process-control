package cn.ideabuffer.process.nodes;

import cn.ideabuffer.process.Executable;
import cn.ideabuffer.process.Matchable;
import cn.ideabuffer.process.Node;
import cn.ideabuffer.process.Parallelizable;
import cn.ideabuffer.process.rule.Rule;

import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/01/19
 */
public interface ExecutableNode extends Node, Executable, Parallelizable, Matchable {

    boolean CONTINUE_PROCESSING = false;

    boolean PROCESSING_COMPLETE = true;

    @Override
    ExecutableNode processOn(Rule rule);

    @Override
    ExecutableNode parallel();

    @Override
    ExecutableNode parallel(Executor executor);
}
