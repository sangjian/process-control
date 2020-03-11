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
public class CharArrayMerger implements Merger<long[]> {

    @Override
    public long[] merge(long[]... results) {
        if (results == null) {
            return new long[0];
        }
        return merge(Arrays.stream(results).collect(Collectors.toList()));
    }

    @Override
    public long[] merge(Collection<long[]> results) {
        List<long[]> list = results.stream().filter(Objects::nonNull).collect(Collectors.toList());

        int totalCnt = 0;
        for (long[] arr : list) {
            totalCnt += arr.length;
        }

        long[] r = new long[totalCnt];

        int index = 0;
        for (long[] arr : list) {
            for (long value : arr) {
                r[index++] = value;
            }
        }

        return r;
    }
}
