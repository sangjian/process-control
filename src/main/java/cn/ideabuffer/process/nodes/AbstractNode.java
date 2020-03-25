package cn.ideabuffer.process.nodes;

import cn.ideabuffer.process.Node;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public abstract class AbstractNode implements Node {

    @Override
    public boolean enabled() {
        return true;
    }
}
