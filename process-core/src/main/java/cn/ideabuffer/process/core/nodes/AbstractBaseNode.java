package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.handler.ExceptionHandler;

/**
 * @author sangjian.sj
 * @date 2020/03/24
 */
public abstract class AbstractBaseNode<R> extends AbstractNode implements BaseNode<R> {

    private ExceptionHandler handler;

    @Override
    public R invoke(Context context) {
        try {
            return doInvoke(context);
        } catch (Exception e) {
            if (handler != null) {
                handler.handle(e);
            }
        }
        return null;
    }

    protected abstract R doInvoke(Context context);

    @Override
    public BaseNode<R> exceptionHandler(ExceptionHandler handler) {
        this.handler = handler;
        return this;
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        return handler;
    }
}
