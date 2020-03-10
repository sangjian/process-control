package cn.ideabuffer.process;

/**
 * @author sangjian.sj
 * @date 2020/03/07
 */
@FunctionalInterface
public interface Mergeable<T> {

    T invoke(Context context) throws Exception;

}
