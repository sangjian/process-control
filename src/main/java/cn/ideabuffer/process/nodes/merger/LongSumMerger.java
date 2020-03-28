package cn.ideabuffer.process.nodes.merger;

import java.util.Collection;
import java.util.Objects;

/**
 * @author sangjian.sj
 * @date 2020/03/11
 */
public class LongSumMerger implements UnitMerger<Long> {

    @Override
    public Long merge(Collection<Long> results) {
        if (results == null) {
            return 0L;
        }

        return results.stream().filter(Objects::nonNull).mapToLong(value -> value).sum();
    }
}
