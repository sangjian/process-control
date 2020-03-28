package cn.ideabuffer.process;

/**
 * 分布式合并结果接口
 *
 * @author sangjian.sj
 * @date 2020/03/07
 */
public interface DistributeMergeable<T, R> extends Mergeable<T> {

    R merge(T t, R r);

}
