package cn.ideabuffer.process;

/**
 * @author sangjian.sj
 * @date 2020/03/07
 */
public interface Merger<T> {

    T merge(T... results);

}
