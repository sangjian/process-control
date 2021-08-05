package cn.ideabuffer.process.core.nodes.merger;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Objects;

/**
 * @author sangjian.sj
 * @date 2020/03/11
 */
public class LongAvgMerger implements UnitMerger<Long> {

    @Override
    public Long merge(@NotNull Collection<Long> results) {
        return (long)results.stream().filter(Objects::nonNull).mapToLong(value -> value).average().orElse(0d);
    }
}
