package cn.ideabuffer.process.core.nodes.merger;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Objects;

/**
 * @author sangjian.sj
 * @date 2020/03/11
 */
public class IntSumMerger implements UnitMerger<Integer> {

    @Override
    public Integer merge(@NotNull Collection<Integer> results) {
        return results.stream().filter(Objects::nonNull).mapToInt(value -> value).sum();
    }

}
