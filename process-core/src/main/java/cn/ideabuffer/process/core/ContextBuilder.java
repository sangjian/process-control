package cn.ideabuffer.process.core;

import cn.ideabuffer.process.core.context.Context;
import org.jetbrains.annotations.NotNull;

public interface ContextBuilder {

    @NotNull
    Context build();

}
