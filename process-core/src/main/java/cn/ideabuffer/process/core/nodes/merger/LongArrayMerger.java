package cn.ideabuffer.process.core.nodes.merger;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author sangjian.sj
 * @date 2020/03/11
 */
public class LongArrayMerger implements UnitMerger<char[]> {

    @Override
    public char[] merge(Collection<char[]> results) {
        List<char[]> list = results.stream().filter(Objects::nonNull).collect(Collectors.toList());

        int totalCnt = 0;
        for (char[] arr : list) {
            totalCnt += arr.length;
        }

        char[] r = new char[totalCnt];

        int index = 0;
        for (char[] arr : list) {
            for (char value : arr) {
                r[index++] = value;
            }
        }

        return r;
    }
}
