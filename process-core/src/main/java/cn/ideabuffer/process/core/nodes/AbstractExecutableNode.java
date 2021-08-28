package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.*;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.context.KeyMapper;
import cn.ideabuffer.process.core.exceptions.ProcessException;
import cn.ideabuffer.process.core.executors.NodeExecutors;
import cn.ideabuffer.process.core.rules.Rule;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public abstract class AbstractExecutableNode<R, P extends Processor<R>> extends AbstractKeyManagerNode
    implements ExecutableNode<R, P> {

    private boolean parallel;
    private Rule rule;
    private Executor executor;
    private P processor;
    private List<ProcessListener<R>> listeners;
    private Key<R> resultKey;
    private ReturnCondition<R> returnCondition;

    protected AbstractExecutableNode() {
        this(false);
    }

    protected AbstractExecutableNode(boolean parallel) {
        this(parallel, null);
    }

    protected AbstractExecutableNode(Rule rule) {
        this(false, rule, null);
    }

    protected AbstractExecutableNode(boolean parallel, Executor executor) {
        this(parallel, null, executor);
    }

    protected AbstractExecutableNode(boolean parallel, Rule rule, Executor executor) {
        this(parallel, rule, executor, null, null);
    }

    protected AbstractExecutableNode(boolean parallel, Rule rule, Executor executor, List<ProcessListener<R>> listeners,
        P processor) {
        this(parallel, rule, executor, listeners, processor, null);
    }

    protected AbstractExecutableNode(boolean parallel, Rule rule, Executor executor, List<ProcessListener<R>> listeners,
        P processor, KeyMapper mapper) {
        this(parallel, rule, executor, listeners, processor, mapper, null);
    }

    protected AbstractExecutableNode(boolean parallel, Rule rule, Executor executor, List<ProcessListener<R>> listeners,
        P processor, KeyMapper mapper, Key<R> resultKey) {
        this(parallel, rule, executor, listeners, processor, mapper, resultKey, null);
    }

    protected AbstractExecutableNode(boolean parallel, Rule rule, Executor executor, List<ProcessListener<R>> listeners,
        P processor, KeyMapper mapper, Key<R> resultKey, ReturnCondition<R> returnCondition) {
        this(parallel, rule, executor, listeners, processor, mapper, resultKey, returnCondition, null, null);
    }

    protected AbstractExecutableNode(boolean parallel, Rule rule, Executor executor, List<ProcessListener<R>> listeners,
        P processor, KeyMapper mapper, Key<R> resultKey, ReturnCondition<R> returnCondition, Set<Key<?>> readableKeys,
        Set<Key<?>> writableKeys) {
        super(mapper, readableKeys, writableKeys);
        this.parallel = parallel;
        this.rule = rule;
        this.executor = executor;
        this.listeners = listeners == null ? new ArrayList<>() : listeners;
        this.processor = processor;
        this.resultKey = resultKey;
        this.returnCondition = returnCondition;
        if (resultKey != null) {
            addWritableKeys(resultKey);
        }
    }

    @Override
    public boolean isParallel() {
        return parallel;
    }

    public void setParallel(boolean parallel) {
        this.parallel = parallel;
    }

    @Override
    public void parallel() {
        this.parallel = true;
    }

    @Override
    public void parallel(Executor executor) {
        this.parallel = true;
        this.executor = executor;
    }

    @Override
    public void processOn(Rule rule) {
        this.rule = rule;
    }

    @Override
    public void registerProcessor(@NotNull P processor) {
        this.processor = processor;
    }

    @Override
    public void addProcessListeners(@NotNull ProcessListener<R>... listeners) {
        this.listeners = Arrays.asList(listeners);
    }

    @Override
    public P getProcessor() {
        return this.processor;
    }

    @Override
    public Rule getRule() {
        return this.rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    @Override
    public List<ProcessListener<R>> getListeners() {
        return Collections.unmodifiableList(listeners);
    }

    public void setListeners(List<ProcessListener<R>> listeners) {
        if (listeners == null || listeners.isEmpty()) {
            return;
        }
        this.listeners = listeners;
    }

    protected boolean ruleCheck(@NotNull Context context) {
        return rule == null || rule.match(context);
    }

    @NotNull
    @Override
    public ProcessStatus execute(Context context) throws Exception {
        if (!enabled()) {
            return ProcessStatus.proceed();
        }
        // 1. 包装context，主要是对key的一些映射和校验
        Context ctx = Contexts.wrap(context, this);
        // 2. 规则校验，校验不通过，不执行当前节点，继续执行下一节点
        if (getProcessor() == null || !ruleCheck(ctx)) {
            return ProcessStatus.proceed();
        }
        ProcessStatus status = ProcessStatus.proceed();
        R result = null;
        // 3. 执行前置操作
        preExecution(ctx);
        // 4. 执行
        if (parallel) {
            doParallelExecute(ctx);
        } else {
            result = doSerialExecute(ctx);
        }
        // 5. 执行后置操作
        postExecution(ctx, result);
        // 6. 如果Processor的返回值类型是ProcessStatus，则直接返回result
        if (result instanceof ProcessStatus) {
            return (ProcessStatus)result;
        }
        // 7. 判断是否满足returnCondition
        if (!parallel && returnCondition != null && returnCondition.onCondition(result)) {
            return ProcessStatus.complete();
        }
        return status;

    }

    protected void preExecution(@NotNull Context context) {

    }

    protected void postExecution(@NotNull Context context, @Nullable R result) {

    }

    @Nullable
    private R doSerialExecute(Context context) throws Exception {
        try {
            R result = getProcessor().process(context);
            if (resultKey != null) {
                if (result != null) {
                    context.put(resultKey, result);
                } else {
                    context.remove(resultKey);
                }
            }
            notifyListeners(context, result, null, true);
            return result;
        } catch (Throwable t) {
            context.setCurrentException(t);
            notifyListeners(context, null, t, false);
            if (t instanceof Exception) {
                throw t;
            } else {
                throw new ProcessException(t);
            }
        }
    }

    private void doParallelExecute(Context context) {
        Executor e = executor == null ? NodeExecutors.DEFAULT_POOL : executor;
        e.execute(() -> {
            try {
                R result = getProcessor().process(context);
                notifyListeners(context, result, null, true);
            } catch (Throwable t) {
                logger.error("doParallelExecute error, node:{}", this, t);
                notifyListeners(context, null, t, false);
            }
        });
    }

    protected void notifyListeners(Context context, @Nullable R result, @Nullable Throwable t, boolean success) {
        if (listeners == null) {
            return;
        }
        listeners.forEach(processListener -> {
            try {
                if (success) {
                    processListener.onComplete(context, result);
                } else {
                    processListener.onFailure(context, t);
                }
            } catch (Throwable ex) {
                logger.error("listener execute error, context:{}, result:{}, exception:{}", context, result, t, ex);
                // ignore
            }
        });
    }

    @Override
    public Executor getExecutor() {
        return executor;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    @Override
    public Key<R> getResultKey() {
        return resultKey;
    }

    @Override
    public void setResultKey(Key<R> resultKey) {
        this.resultKey = resultKey;
        if (resultKey != null) {
            super.addWritableKeys(resultKey);
        }
    }

    @Override
    public void returnOn(ReturnCondition<R> condition) {
        this.returnCondition = condition;
    }

    @Override
    public ReturnCondition<R> getReturnCondition() {
        return returnCondition;
    }

    @Override
    public void initialize() {
        super.initialize();
        P processor = getProcessor();
        if (processor instanceof Lifecycle) {
            LifecycleManager.initialize((Lifecycle)processor);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        if (executor instanceof ExecutorService && !((ExecutorService)executor).isShutdown()) {
            ((ExecutorService)executor).shutdown();
        }
        P processor = getProcessor();
        if (processor instanceof Lifecycle) {
            LifecycleManager.destroy((Lifecycle)processor);
        }
    }

    @Override
    public String getName() {
        return name != null ? name : this.getClass().getSimpleName() + "-" + getProcessor().getClass().getSimpleName();
    }
}
