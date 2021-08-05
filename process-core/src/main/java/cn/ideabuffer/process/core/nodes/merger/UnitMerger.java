package cn.ideabuffer.process.core.nodes.merger;

/**
 * 单元化合并器，输入类型和输出类型一致
 *
 * @param <R> 合并输入类型/合并后输出类型
 * @author sangjian.sj
 * @date 2020/03/27
 */
public interface UnitMerger<R> extends Merger<R, R> {
}
