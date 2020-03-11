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
public class ShortArrayMerger implements Merger<short[]> {

    @Override
    public short[] merge(short[]... results) {
        if (results == null) {
            return new short[0];
        }
        return merge(Arrays.stream(results).collect(Collectors.toList()));
    }

    @Override
    public short[] merge(Collection<short[]> results) {
        List<short[]> list = results.stream().filter(Objects::nonNull).collect(Collectors.toList());

        int totalCnt = 0;
        for (short[] arr : list) {
            totalCnt += arr.length;
        }

        short[] r = new short[totalCnt];

        int index = 0;
        for (short[] arr : list) {
            for (short value : arr) {
                r[index++] = value;
            }
        }

        return r;
    }
}
