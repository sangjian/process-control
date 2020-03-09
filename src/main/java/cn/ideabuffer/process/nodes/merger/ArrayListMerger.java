package cn.ideabuffer.process.nodes.merger;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author sangjian.sj
 * @date 2020/03/09
 */
public class ArrayListMerger<T> implements ListMerger<T> {

    @Override
    public ArrayList<T> merge(List<T>... results) {
        if(results == null) {
            return new ArrayList<>();
        }
        return merge(Arrays.stream(results).collect(Collectors.toList()));
    }

    @Override
    public ArrayList<T> merge(Collection<List<T>> results) {
        if(results == null) {
            return new ArrayList<>();
        }
        ArrayList<T> list = new ArrayList<>();
        for (List<T> item : results) {
            list.addAll(item);
        }
        return list;
    }
}
