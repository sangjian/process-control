package cn.ideabuffer.process.core.nodes.wrapper;

import cn.ideabuffer.process.core.LifecycleState;
import cn.ideabuffer.process.core.ProcessListener;
import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.ReturnCondition;
import cn.ideabuffer.process.core.context.*;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.rule.Rule;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;

/**
 * @author sangjian.sj
 * @date 2021/06/12
 */
public class WrapperProxy<R, P extends Processor<R>> implements ExecutableNode<R, P> {

    /**
     * 被代理的目标节点
     */
    private ExecutableNode<R, P> target;

    /**
     * 包装处理对象
     */
    private WrapperHandler<R> handler;

    public WrapperProxy(ExecutableNode<R, P> target,
        WrapperHandler<R> handler) {
        this.target = target;
        this.handler = handler;
    }

    @SafeVarargs
    public static <R1, P1 extends Processor<R1>> ExecutableNode<R1, P1> wrap(@NotNull ExecutableNode<R1, P1> node,
        @NotNull WrapperHandler<R1>... handlers) {
        ExecutableNode<R1, P1> wrapped = node;
        for (int i = handlers.length - 1; i >= 0; i--) {
            wrapped = new WrapperProxy<>(wrapped, handlers[i]);
        }
        return wrapped;
    }

    @Override
    public boolean isParallel() {return target.isParallel();}

    @Override
    public void registerProcessor(P processor) {target.registerProcessor(processor);}

    @Override
    public void addProcessListeners(
        @NotNull ProcessListener<R>... listeners) {target.addProcessListeners(listeners);}

    @Override
    public P getProcessor() {return target.getProcessor();}

    @Override
    public List<ProcessListener<R>> getListeners() {return target.getListeners();}

    @Override
    public KeyMapper getKeyMapper() {return target.getKeyMapper();}

    @Override
    public void setKeyMapper(KeyMapper mapper) {target.setKeyMapper(mapper);}

    @Override
    public void setResultKey(Key<R> resultKey) {target.setResultKey(resultKey);}

    @Override
    public Key<R> getResultKey() {return target.getResultKey();}

    @Override
    public void returnOn(ReturnCondition<R> condition) {target.returnOn(condition);}

    @Override
    public ReturnCondition<R> getReturnCondition() {return target.getReturnCondition();}

    @Override
    public void setReadableKeys(Set<Key<?>> keys) {target.setReadableKeys(keys);}

    @Override
    public Set<Key<?>> getReadableKeys() {return target.getReadableKeys();}

    @Override
    public void setWritableKeys(Set<Key<?>> keys) {target.setWritableKeys(keys);}

    @Override
    public Set<Key<?>> getWritableKeys() {return target.getWritableKeys();}

    @Override
    public boolean enabled() {return target.enabled();}

    @Override
    public void setEnabled(boolean enable) {target.setEnabled(enable);}

    @Override
    public void setEnabled(BooleanSupplier supplier) {target.setEnabled(supplier);}

    @Override
    public void initialize() {target.initialize();}

    @Override
    public void destroy() {target.destroy();}

    @Override
    @NotNull
    public LifecycleState getState() {return target.getState();}

    @Override
    @NotNull
    public ProcessStatus execute(Context context) throws Exception {
        Context ctx = Contexts.wrap(context, target);
        try {
            handler.before(ctx);
            ProcessStatus status = target.execute(ctx);
            R result = target.getResultKey() != null ? ctx.get(target.getResultKey()) : null;
            handler.afterReturning(ctx, result, status);
            return status;
        } catch (Throwable t) {
            handler.afterThrowing(ctx, t);
            throw t;
        }
    }

    @Override
    public Executor getExecutor() {return target.getExecutor();}

    @Override
    public void parallel() {target.parallel();}

    @Override
    public void parallel(Executor executor) {target.parallel(executor);}

    @Override
    public void processOn(Rule rule) {target.processOn(rule);}

    @Override
    public Rule getRule() {return target.getRule();}
}
