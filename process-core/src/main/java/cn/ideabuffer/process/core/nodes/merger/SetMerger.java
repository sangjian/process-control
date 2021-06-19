package cn.ideabuffer.process.core.nodes.merger;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Set;

/**
 * Set类型合并器
 *
 * @param <T> Set元素类型
 * @author sangjian.sj
 * @date 2020/03/09
 */
public interface SetMerger<T> extends UnitMerger<Set<T>> {

    @Override
    Set<T> merge(@NotNull Collection<Set<T>> results);
}
