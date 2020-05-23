package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.processors.ResultProcessor;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/03/24
 */
public abstract class AbstractBaseNode<R> extends AbstractNode implements BaseNode<R> {

    private ResultProcessor<R> processor;

    public AbstractBaseNode() {
    }

    public AbstractBaseNode(ResultProcessor<R> processor) {
        this.processor = processor;
    }

    @Override
    public R invoke(@NotNull Context context, @NotNull ProcessStatus status) {
        return processor.process(context, status);
    }

    @Override
    public ResultProcessor<R> getProcessor() {
        return processor;
    }

    @Override
    public void setProcessor(ResultProcessor<R> processor) {
        this.processor = processor;
    }
}
