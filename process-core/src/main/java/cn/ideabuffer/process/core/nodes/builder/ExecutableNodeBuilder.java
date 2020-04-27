package cn.ideabuffer.process.core.nodes.builder;

import cn.ideabuffer.process.core.handler.ExceptionHandler;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.rule.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/04/24
 */
public class ExecutableNodeBuilder extends AbstractExecutableNodeBuilder<ExecutableNode> {

    ExecutableNodeBuilder(ExecutableNode node) {
        super(node);
    }

    public static ExecutableNodeBuilder newBuilder(@NotNull ExecutableNode node) {
        return new ExecutableNodeBuilder(node);
    }

    @Override
    public ExecutableNodeBuilder exceptionHandler(ExceptionHandler handler) {
        super.exceptionHandler(handler);
        return this;
    }

    @Override
    public ExecutableNodeBuilder parallel() {
        super.parallel();
        return this;
    }

    @Override
    public ExecutableNodeBuilder parallel(Executor executor) {
        super.parallel(executor);
        return this;
    }

    @Override
    public ExecutableNodeBuilder processOn(Rule rule) {
        super.processOn(rule);
        return this;
    }

}
