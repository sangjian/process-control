package cn.ideabuffer.process.nodes.merger;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author sangjian.sj
 * @date 2020/03/11
 */
public class IntSumMerger implements Merger<Integer> {

    @Override
    public Integer merge(Integer... results) {
        if (results == null) {
            return 0;
        }
        return merge(Arrays.stream(results).collect(Collectors.toList()));
    }

    @Override
    public Integer merge(Collection<Integer> results) {
        if (results == null) {
            return 0;
        }

        return results.stream().filter(Objects::nonNull).mapToInt(value -> value).sum();
    }
}
