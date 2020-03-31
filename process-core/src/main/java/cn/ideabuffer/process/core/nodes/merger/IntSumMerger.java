package cn.ideabuffer.process.core.nodes.merger;

import java.util.Collection;
import java.util.Objects;

/**
 * @author sangjian.sj
 * @date 2020/03/11
 */
public class IntSumMerger implements UnitMerger<Integer> {

    @Override
    public Integer merge(Collection<Integer> results) {
        if (results == null) {
            return 0;
        }

        return results.stream().filter(Objects::nonNull).mapToInt(value -> value).sum();
    }
}
