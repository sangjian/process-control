package cn.ideabuffer.process;

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
