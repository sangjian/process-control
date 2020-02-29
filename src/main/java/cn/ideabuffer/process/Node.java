package cn.ideabuffer.process;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public interface Node {

    /**
     * 节点ID
     *
     * @return
     */
    String getId();

    /**
     * 是否启用
     *
     * @param context 流程上下文
     * @return
     */
    boolean enabled(Context context);

}
