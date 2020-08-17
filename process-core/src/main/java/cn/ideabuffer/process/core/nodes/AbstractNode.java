package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.LifecycleState;
import cn.ideabuffer.process.core.Node;
import cn.ideabuffer.process.core.exception.LifecycleException;
import org.jetbrains.annotations.NotNull;

import java.util.function.BooleanSupplier;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public abstract class AbstractNode implements Node {

    private volatile LifecycleState state = LifecycleState.NEW;

    /**
     * 默认开启
     */
    private BooleanSupplier enableSupplier = () -> true;

    @Override
    public boolean enabled() {
        if (enableSupplier != null) {
            return enableSupplier.getAsBoolean();
        }
        return false;
    }

    @Override
    public void setEnabled(boolean enable) {
        this.enableSupplier = () -> enable;
    }

    @Override
    public void setEnabled(BooleanSupplier supplier) {
        this.enableSupplier = supplier;
    }

    @Override
    public void destroy() {
        if (state != LifecycleState.INITIALIZED) {
            return;
        }
        synchronized (this) {
            if (state != LifecycleState.INITIALIZED) {
                return;
            }
            try {
                setState(LifecycleState.DESTROYING);
                onDestroy();
                setState(LifecycleState.DESTROYED);
            } catch (Exception e) {
                handleException(e, "destroy failed!");
            }
        }
    }

    @Override
    public void initialize() {
        if (state != LifecycleState.NEW) {
            return;
        }
        synchronized (this) {
            if (state != LifecycleState.NEW) {
                return;
            }
            try {
                setState(LifecycleState.INITIALIZING);
                onInitialize();
                setState(LifecycleState.INITIALIZED);
            } catch (Exception e) {
                handleException(e, "initialize failed!");
            }
        }
    }

    @NotNull
    @Override
    public LifecycleState getState() {
        return state;
    }

    protected void setState(LifecycleState state) {
        this.state = state;
    }

    protected void handleException(Throwable t, String msg) {
        setState(LifecycleState.FAILED);
        throw new LifecycleException(msg, t);
    }

    protected void onDestroy() {

    }

    protected void onInitialize() {

    }
}
