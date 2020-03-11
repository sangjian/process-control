package cn.ideabuffer.process.nodes.merger;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author sangjian.sj
 * @date 2020/03/11
 */
public class BooleanArrayMerger implements Merger<boolean[]> {

    @Override
    public boolean[] merge(boolean[]... results) {
        if (results == null) {
            return new boolean[0];
        }
        return merge(Arrays.stream(results).collect(Collectors.toList()));
    }

    @Override
    public boolean[] merge(Collection<boolean[]> results) {
        List<boolean[]> list = results.stream().filter(Objects::nonNull).collect(Collectors.toList());

        int totalCnt = 0;
        for (boolean[] arr : list) {
            totalCnt += arr.length;
        }

        boolean[] r = new boolean[totalCnt];

        int index = 0;
        for (boolean[] arr : list) {
            for (boolean value : arr) {
                r[index++] = value;
            }
        }

        return r;
    }
}
