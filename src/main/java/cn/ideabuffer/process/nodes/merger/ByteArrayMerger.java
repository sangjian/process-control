package cn.ideabuffer.process.nodes.merger;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author sangjian.sj
 * @date 2020/03/11
 */
public class ByteArrayMerger implements UnitMerger<byte[]> {

    @Override
    public byte[] merge(Collection<byte[]> results) {
        List<byte[]> list = results.stream().filter(Objects::nonNull).collect(Collectors.toList());

        int totalCnt = 0;
        for (byte[] arr : list) {
            totalCnt += arr.length;
        }

        byte[] r = new byte[totalCnt];

        int index = 0;
        for (byte[] arr : list) {
            for (byte value : arr) {
                r[index++] = value;
            }
        }

        return r;
    }
}
