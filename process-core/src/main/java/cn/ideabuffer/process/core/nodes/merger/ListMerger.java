package cn.ideabuffer.process.core.nodes.merger;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/03/09
 */
public interface ListMerger<T> extends UnitMerger<List<T>> {

    @Override
    List<T> merge(@NotNull Collection<List<T>> results);
}
