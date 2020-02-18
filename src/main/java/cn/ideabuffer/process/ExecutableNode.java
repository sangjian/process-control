package cn.ideabuffer.process;

/**
 * @author sangjian.sj
 * @date 2020/01/19
 */
public interface ExecutableNode extends Node {

    boolean execute(Context context) throws Exception;

}
