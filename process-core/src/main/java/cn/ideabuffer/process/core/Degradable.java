package cn.ideabuffer.process.core;

import org.jetbrains.annotations.NotNull;

import java.util.function.BooleanSupplier;

public interface Degradable<R, P extends Processor<R>> {
    /**
     * 强依赖
     */
    BooleanSupplier STRONG_DEPENDENCY = () -> false;

    /**
     * 弱依赖
     */
    BooleanSupplier WEAK_DEPENDENCY = () -> true;

    boolean isWeakDependency();

    @NotNull
    P getFallbackProcessor();
}
