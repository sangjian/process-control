package cn.ideabuffer.process;

/**
 * @author sangjian.sj
 * @date 2020/01/19
 */
public interface PostProcessor {

    boolean postProcess(Context context, Exception e);

}
