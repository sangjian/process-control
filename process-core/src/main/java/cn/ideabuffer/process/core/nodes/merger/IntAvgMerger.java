package cn.ideabuffer.process.core.nodes.merger;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Objects;

/**
 * @author sangjian.sj
 * @date 2020/03/11
 */
public class IntAvgMerger implements UnitMerger<Integer> {

    @Override
    public Integer merge(@NotNull Collection<Integer> results) {
        return (int)results.stream().filter(Objects::nonNull).mapToInt(value -> value).average().orElse(0);
    }
}
