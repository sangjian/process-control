package cn.ideabuffer.process.core.nodes.merger;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Objects;

/**
 * @author sangjian.sj
 * @date 2020/03/11
 */
public class LongSumMerger implements UnitMerger<Long> {

    @Override
    public Long merge(@NotNull Collection<Long> results) {
        if (results == null) {
            return 0L;
        }

        return results.stream().filter(Objects::nonNull).mapToLong(value -> value).sum();
    }
}
