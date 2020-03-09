package cn.ideabuffer.process.nodes.branch;

import cn.ideabuffer.process.Branch;
import cn.ideabuffer.process.nodes.MergeableNode;
import cn.ideabuffer.process.Node;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/03/07
 */
public interface MergeableBranchNode<T> extends Node, Branch<MergeableNode<T>> {

    @Override
    Branch addNodes(@NotNull MergeableNode<T>... nodes);

    @Override
    List<MergeableNode<T>> getNodes();
}
