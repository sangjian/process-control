package cn.ideabuffer.process.core;

import cn.ideabuffer.process.core.exceptions.LifecycleException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static cn.ideabuffer.process.core.LifecycleState.*;

/**
 * @author sangjian.sj
 * @date 2021/06/26
 */
public class LifecycleManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(LifecycleManager.class);

    private static Map<Lifecycle, LifecycleState> INITIALIZING_MAP = new ConcurrentHashMap<>();
    private static Map<Lifecycle, LifecycleState> DESTROYING_MAP = new ConcurrentHashMap<>();

    public static void initialize(@NotNull List<? extends Lifecycle> components) {
        components.forEach(LifecycleManager::initialize);
    }

    public static void initialize(@NotNull Lifecycle component) {
        LifecycleState value = INITIALIZING_MAP.putIfAbsent(component, NEW);
        if (value != null) {
            return;
        }
        try {
            INITIALIZING_MAP.put(component, INITIALIZING);
            component.initialize();
            INITIALIZING_MAP.put(component, LifecycleState.INITIALIZED);
        } catch (Throwable t) {
            LOGGER.error("initialize component error!", t);
            INITIALIZING_MAP.put(component, INITIALIZING_FAILED);
            throw new LifecycleException("initialize component error!", t);
        }
    }

    public static void destroy(@NotNull List<? extends Lifecycle> components) {
        components.forEach(LifecycleManager::destroy);
    }

    public static void destroy(@NotNull Lifecycle component) {
        LifecycleState value = DESTROYING_MAP.putIfAbsent(component, DESTROYING);
        if (value != null) {
            return;
        }
        try {
            component.destroy();
            DESTROYING_MAP.put(component, DESTROYED);
        } catch (Throwable t) {
            LOGGER.error("destroy component error!", t);
            DESTROYING_MAP.put(component, DESTROYING_FAILED);
            throw new LifecycleException("destroy component error!", t);
        }
    }
}
