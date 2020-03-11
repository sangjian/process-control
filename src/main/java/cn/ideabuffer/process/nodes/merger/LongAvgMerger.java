package cn.ideabuffer.process.nodes.merger;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author sangjian.sj
 * @date 2020/03/11
 */
public class LongAvgMerger implements Merger<Long> {

    @Override
    public Long merge(Long... results) {
        if (results == null) {
            return 0L;
        }
        return merge(Arrays.stream(results).collect(Collectors.toList()));
    }

    @Override
    public Long merge(Collection<Long> results) {
        if (results == null) {
            return 0L;
        }

        return (long)results.stream().filter(Objects::nonNull).mapToLong(value -> value).average().orElse(0d);
    }
}
