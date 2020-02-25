package cn.ideabuffer.process;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public interface Node {

    String getId();

    boolean enabled(Context context);

}
