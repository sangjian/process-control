package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.Matchable;
import cn.ideabuffer.process.core.Retrieable;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/04/27
 */
public interface RetrieableNode<R> extends ExecutableNode, Retrieable<R>, Matchable {

    ProcessStatus onComplete(@NotNull Context context, R result);

    ProcessStatus onFailure(@NotNull Context context, Throwable t);

}
