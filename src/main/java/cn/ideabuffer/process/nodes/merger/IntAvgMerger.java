package cn.ideabuffer.process.nodes.merger;

import java.util.Collection;
import java.util.Objects;

/**
 * @author sangjian.sj
 * @date 2020/03/11
 */
public class IntAvgMerger implements UnitMerger<Integer> {

    @Override
    public Integer merge(Collection<Integer> results) {
        if (results == null) {
            return 0;
        }

        return (int)results.stream().filter(Objects::nonNull).mapToInt(value -> value).average().orElse(0);
    }
}
