package cn.ideabuffer.process.core.nodes.merger;

import java.util.Collection;
import java.util.Objects;

/**
 * @author sangjian.sj
 * @date 2020/03/11
 */
public class LongAvgMerger implements UnitMerger<Long> {

    @Override
    public Long merge(Collection<Long> results) {
        if (results == null) {
            return 0L;
        }

        return (long)results.stream().filter(Objects::nonNull).mapToLong(value -> value).average().orElse(0d);
    }
}
