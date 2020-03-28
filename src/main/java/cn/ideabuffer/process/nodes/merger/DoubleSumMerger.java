package cn.ideabuffer.process.nodes.merger;

import java.util.Collection;
import java.util.Objects;

/**
 * @author sangjian.sj
 * @date 2020/03/11
 */
public class DoubleSumMerger implements UnitMerger<Double> {

    @Override
    public Double merge(Collection<Double> results) {
        if (results == null) {
            return 0d;
        }

        return results.stream().filter(Objects::nonNull).mapToDouble(value -> value).sum();
    }
}
