package cn.ideabuffer.process;

import cn.ideabuffer.process.Context;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public interface Node {

    String getId();

    boolean enabled(Context context);

}
