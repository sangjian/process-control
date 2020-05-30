package cn.ideabuffer.process.core.nodes.merger;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Objects;

/**
 * @author sangjian.sj
 * @date 2020/03/11
 */
public class DoubleAvgMerger implements UnitMerger<Double> {

    @Override
    public Double merge(@NotNull Collection<Double> results) {
        return results.stream().filter(Objects::nonNull).mapToDouble(value -> value).average().orElse(0d);
    }
}
