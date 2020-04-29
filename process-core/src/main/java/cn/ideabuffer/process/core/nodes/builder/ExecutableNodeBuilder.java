package cn.ideabuffer.process.core.nodes.builder;

import cn.ideabuffer.process.core.ProcessListener;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.rule.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/04/24
 */
public class ExecutableNodeBuilder extends AbstractExecutableNodeBuilder<ExecutableNode> {

    private ExecutableNodeBuilder(ExecutableNode node) {
        super(node);
    }

    public static ExecutableNodeBuilder newBuilder(@NotNull ExecutableNode node) {
        return new ExecutableNodeBuilder(node);
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

    @Override
    public ExecutableNodeBuilder addListeners(ProcessListener... listeners) {
        super.addListeners(listeners);
        return this;
    }
}
