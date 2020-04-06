package cn.ideabuffer.process.core;

/**
 * @author sangjian.sj
 * @date 2020/04/06
 */
public enum LifecycleState {
    NEW(false),
    INITIALIZING(false),
    INITIALIZED(true),
    DESTROYING(false),
    DESTROYED(false),
    FAILED(false);

    private final boolean available;

    LifecycleState(boolean available) {
        this.available = available;
    }

    public boolean isAvailable() {
        return available;
    }
}
