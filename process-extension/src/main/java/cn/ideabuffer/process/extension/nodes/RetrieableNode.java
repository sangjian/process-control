package cn.ideabuffer.process.extension.nodes;

import cn.ideabuffer.process.core.Matchable;
import cn.ideabuffer.process.extension.Retrieable;
import cn.ideabuffer.process.core.nodes.ExecutableNode;

/**
 * @author sangjian.sj
 * @date 2020/04/27
 */
public interface RetrieableNode<R> extends ExecutableNode<R>, Matchable, Retrieable<R> {

}
