package cn.ideabuffer.process.core.nodes.merger;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author sangjian.sj
 * @date 2020/03/11
 */
public class IntArrayMerger implements UnitMerger<int[]> {

    @Override
    public int[] merge(Collection<int[]> results) {
        List<int[]> list = results.stream().filter(Objects::nonNull).collect(Collectors.toList());

        int totalCnt = 0;
        for (int[] arr : list) {
            totalCnt += arr.length;
        }

        int[] r = new int[totalCnt];

        int index = 0;
        for (int[] arr : list) {
            for (int value : arr) {
                r[index++] = value;
            }
        }

        return r;
    }
}
