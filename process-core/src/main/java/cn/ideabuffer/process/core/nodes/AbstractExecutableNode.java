package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.*;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.context.KeyMapper;
import cn.ideabuffer.process.core.executor.NodeExecutors;
import cn.ideabuffer.process.core.rule.Rule;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public abstract class AbstractExecutableNode<R, P extends Processor<R>> extends AbstractNode
    implements ExecutableNode<R, P> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private boolean parallel = false;
    private Rule rule;
    private Executor executor;
    private P processor;
    private List<ProcessListener<R>> listeners;
    private KeyMapper mapper;
    private Key<R> resultKey;
    private ReturnCondition<R> returnCondition;
    private Set<Key<?>> readableKeys;
    private Set<Key<?>> writableKeys;

    public AbstractExecutableNode() {
        this(false);
    }

    public AbstractExecutableNode(boolean parallel) {
        this(parallel, null);
    }

    public AbstractExecutableNode(Rule rule) {
        this(false, rule, null);
    }

    public AbstractExecutableNode(boolean parallel, Executor executor) {
        this(parallel, null, executor);
    }

    public AbstractExecutableNode(boolean parallel, Rule rule, Executor executor) {
        this(parallel, rule, executor, null, null);
    }

    public AbstractExecutableNode(boolean parallel, Rule rule, Executor executor, List<ProcessListener<R>> listeners,
        P processor) {
        this(parallel, rule, executor, listeners, processor, null);
    }

    public AbstractExecutableNode(boolean parallel, Rule rule, Executor executor, List<ProcessListener<R>> listeners, P processor, KeyMapper mapper) {
        this(parallel, rule, executor, listeners, processor, mapper, null);
    }

    public AbstractExecutableNode(boolean parallel, Rule rule, Executor executor, List<ProcessListener<R>> listeners, P processor, KeyMapper mapper, Key<R> resultKey) {
        this(parallel, rule, executor, listeners, processor, mapper, resultKey, null);
    }

    public AbstractExecutableNode(boolean parallel, Rule rule, Executor executor, List<ProcessListener<R>> listeners, P processor, KeyMapper mapper, Key<R> resultKey, ReturnCondition<R> returnCondition) {
        this(parallel, rule, executor, listeners, processor, mapper, resultKey, returnCondition, null, null);
    }

    public AbstractExecutableNode(boolean parallel, Rule rule, Executor executor, List<ProcessListener<R>> listeners, P processor, KeyMapper mapper, Key<R> resultKey, ReturnCondition<R> returnCondition, Set<Key<?>> readableKeys, Set<Key<?>> writableKeys) {
        this.parallel = parallel;
        this.rule = rule;
        this.executor = executor;
        this.listeners = listeners;
        this.processor = processor;
        this.mapper = mapper;
        this.resultKey = resultKey;
        this.returnCondition = returnCondition;
        this.readableKeys = readableKeys;
        this.writableKeys = writableKeys == null ? new HashSet<>() : writableKeys;
        if (resultKey != null) {
            this.writableKeys.add(resultKey);
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
    public void registerProcessor(P processor) {
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
        return listeners;
    }

    public void setListeners(List<ProcessListener<R>> listeners) {
        this.listeners = listeners;
    }

    @Override
    public KeyMapper getKeyMapper() {
        return mapper;
    }

    @Override
    public void setKeyMapper(KeyMapper mapper) {
        this.mapper = mapper;
    }

    protected boolean ruleCheck(@NotNull Context context) {
        return rule == null || rule.match(context);
    }

    @NotNull
    @Override
    public ProcessStatus execute(Context context) throws Exception {
        Context ctx = Contexts.wrap(context, context.getBlock(), mapper, readableKeys, writableKeys);
        if (getProcessor() == null || !ruleCheck(ctx)) {
            return ProcessStatus.proceed();
        }

        if (parallel) {
            doParallelExecute(ctx);
            return ProcessStatus.proceed();
        }
        try {
            R result = getProcessor().process(ctx);
            if (resultKey != null) {
                ctx.put(resultKey, result);
            }
            notifyListeners(ctx, result, null, true);
            // 判断是否满足returnCondition
            if (returnCondition != null && returnCondition.onCondition(result)) {
                return ProcessStatus.complete();
            }
        } catch (Exception e) {
            notifyListeners(ctx, null, e, false);
            throw e;
        }


        return ProcessStatus.proceed();
    }

    private void doParallelExecute(Context context) {
        Executor e = executor == null ? NodeExecutors.DEFAULT_POOL : executor;
        e.execute(() -> {
            try {
                R result = getProcessor().process(context);
                notifyListeners(context, result, null, true);
            } catch (Exception ex) {
                logger.error("doParallelExecute error, node:{}", this, ex);
                notifyListeners(context, null, ex, false);
            }
        });
    }

    protected void notifyListeners(Context context, @Nullable R result, Exception e, boolean success) {
        if (listeners == null) {
            return;
        }
        listeners.forEach(processListener -> {
            try {
                if (success) {
                    processListener.onComplete(context, result);
                } else {
                    processListener.onFailure(context, e);
                }
            } catch (Exception ex) {
                logger.error("listener execute error, context:{}, result:{}, exception:{}", context, result, e, ex);
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
    public void setResultKey(Key<R> resultKey) {
        this.resultKey = resultKey;
        if (resultKey != null) {
            this.writableKeys.add(resultKey);
        }
    }

    @Override
    public Key<R> getResultKey() {
        return resultKey;
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
    public void setReadableKeys(Set<Key<?>> keys) {
        this.readableKeys = keys;
    }

    @Override
    public Set<Key<?>> getReadableKeys() {
        return readableKeys;
    }

    @Override
    public void setWritableKeys(Set<Key<?>> keys) {
        this.writableKeys = keys == null ? new HashSet<>() : keys;
        if (this.resultKey != null) {
            this.writableKeys.add(this.resultKey);
        }
    }

    @Override
    public Set<Key<?>> getWritableKeys() {
        return writableKeys;
    }

    @Override
    public final void destroy() {
        if (getState() != LifecycleState.INITIALIZED) {
            return;
        }
        synchronized (this) {
            if (getState() != LifecycleState.INITIALIZED) {
                return;
            }
            try {
                setState(LifecycleState.DESTROYING);
                if (executor instanceof ExecutorService && !((ExecutorService)executor).isShutdown()) {
                    ((ExecutorService)executor).shutdown();
                }
                onDestroy();
                setState(LifecycleState.DESTROYED);
            } catch (Exception e) {
                handleException(e, "destroy failed!");
            }
        }
    }

}
