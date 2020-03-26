package cn.ideabuffer.process;

import cn.ideabuffer.process.context.Context;

/**
 * @author sangjian.sj
 * @date 2020/01/19
 */
public interface PostProcessor {

    boolean postProcess(Context context, Exception e);

}
