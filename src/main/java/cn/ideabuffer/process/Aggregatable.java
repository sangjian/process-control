package cn.ideabuffer.process;

/**
 * @author sangjian.sj
 * @date 2020/03/07
 */
public interface Aggregatable {

    void aggregate(Context context) throws Exception;

}
