package cn.ideabuffer.process.core;

import cn.ideabuffer.process.core.context.Context;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/04/30
 */
public interface Processor<V> {

    V process(@NotNull Context context) throws Exception;

}
