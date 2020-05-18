package cn.ideabuffer.process.core.nodes.merger;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/03/09
 */
public class ArrayListMerger<T> implements ListMerger<T> {

    @Override
    public ArrayList<T> merge(@NotNull Collection<List<T>> results) {
        ArrayList<T> list = new ArrayList<>();
        for (List<T> item : results) {
            list.addAll(item);
        }
        return list;
    }
}
