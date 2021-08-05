package cn.ideabuffer.process.core.test.merger;

import cn.ideabuffer.process.core.nodes.merger.Merger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/04/11
 */
public class TestStringListMerger implements Merger<String, List<String>> {

    @Override
    public List<String> merge(@NotNull Collection<String> results) {
        return new ArrayList<>(results);
    }
}
