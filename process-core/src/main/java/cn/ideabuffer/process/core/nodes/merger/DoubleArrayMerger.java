package cn.ideabuffer.process.core.nodes.merger;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author sangjian.sj
 * @date 2020/03/11
 */
public class DoubleArrayMerger implements UnitMerger<double[]> {

    @Override
    public double[] merge(@NotNull Collection<double[]> results) {
        List<double[]> list = results.stream().filter(Objects::nonNull).collect(Collectors.toList());

        int totalCnt = 0;
        for (double[] arr : list) {
            totalCnt += arr.length;
        }

        double[] r = new double[totalCnt];

        int index = 0;
        for (double[] arr : list) {
            for (double value : arr) {
                r[index++] = value;
            }
        }

        return r;
    }
}
