package cn.ideabuffer.process;

/**
 * 通用节点
 *
 * @author sangjian.sj
 * @date 2020/01/18
 */
public interface Node {

    /**
     * 是否启用
     *
     * @param context 流程上下文
     * @return <li>true: 启用</li><li>false: 关闭</li>
     */
    boolean enabled(Context context);

}
