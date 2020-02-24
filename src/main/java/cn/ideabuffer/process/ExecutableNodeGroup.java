package cn.ideabuffer.process;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public interface ExecutableNodeGroup extends Node{

    ExecutableNode[] getNodes();

    ExecutableNodeGroup addNode(ExecutableNode node);
}
