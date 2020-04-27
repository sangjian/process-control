package cn.ideabuffer.process.core;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 分支节点
 *
 * @author sangjian.sj
 * @date 2020/01/19
 */
public interface Branch<T extends Node> {

    /**
     * 添加节点
     *
     * @param nodes 节点数组
     */
    void addNodes(@NotNull T... nodes);

    /**
     * 获取分支下的所有节点
     *
     * @return 节点列表
     */
    List<T> getNodes();
}