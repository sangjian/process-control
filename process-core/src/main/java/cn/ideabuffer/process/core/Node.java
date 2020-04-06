package cn.ideabuffer.process.core;

/**
 * 通用节点
 *
 * @author sangjian.sj
 * @date 2020/01/18
 */
public interface Node extends Lifecycle {

    /**
     * 是否启用
     *
     * @return <li>true: 启用</li><li>false: 关闭</li>
     */
    boolean enabled();

}
