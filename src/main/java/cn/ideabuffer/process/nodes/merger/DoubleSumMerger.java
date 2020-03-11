package cn.ideabuffer.process.nodes.merger;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author sangjian.sj
 * @date 2020/03/11
 */
public class DoubleSumMerger implements Merger<Double> {

    @Override
    public Double merge(Double... results) {
        if (results == null) {
            return 0d;
        }
        return merge(Arrays.stream(results).collect(Collectors.toList()));
    }

    @Override
    public Double merge(Collection<Double> results) {
        if (results == null) {
            return 0d;
        }

        return results.stream().filter(Objects::nonNull).mapToDouble(value -> value).sum();
    }
}
