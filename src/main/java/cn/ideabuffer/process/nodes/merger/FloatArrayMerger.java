package cn.ideabuffer.process.nodes.merger;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author sangjian.sj
 * @date 2020/03/11
 */
public class FloatArrayMerger implements UnitMerger<float[]> {

    @Override
    public float[] merge(Collection<float[]> results) {
        List<float[]> list = results.stream().filter(Objects::nonNull).collect(Collectors.toList());

        int totalCnt = 0;
        for (float[] arr : list) {
            totalCnt += arr.length;
        }

        float[] r = new float[totalCnt];

        int index = 0;
        for (float[] arr : list) {
            for (float value : arr) {
                r[index++] = value;
            }
        }

        return r;
    }
}
