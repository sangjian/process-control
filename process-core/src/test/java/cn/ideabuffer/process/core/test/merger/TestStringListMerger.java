package cn.ideabuffer.process.core.test.merger;

import cn.ideabuffer.process.core.nodes.merger.Merger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/04/11
 */
public class TestStringListMerger implements Merger<String, List<String>> {

    @Override
    public List<String> merge(Collection<String> results) {
        if (results == null) {
            return null;
        }

        return new ArrayList<>(results);
    }
}
