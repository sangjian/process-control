package cn.ideabuffer.process.api.model.listener;

import cn.ideabuffer.process.api.model.Model;
import cn.ideabuffer.process.core.NodeListener;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/06/20
 */
public class NodeListenerModel<R extends NodeListener> extends Model<R> {

    private static final long serialVersionUID = -808431624054431943L;

    public NodeListenerModel(@NotNull R listener) {
        super(listener);
    }
}
