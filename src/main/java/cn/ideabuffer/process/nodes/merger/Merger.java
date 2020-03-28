package cn.ideabuffer.process.nodes.merger;

import java.util.Collection;

/**
 * 结果合并器
 *
 * @param <P> 需要合并的输入类型
 * @param <R> 合并结果类型
 * @author sangjian.sj
 * @date 2020/03/07
 */
public interface Merger<P, R> {

    /**
     * 结果合并
     * @param results 待合并的结果集合
     * @return 合并结果
     */
    R merge(Collection<P> results);

}
