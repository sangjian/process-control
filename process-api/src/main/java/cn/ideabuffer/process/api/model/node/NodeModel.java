package cn.ideabuffer.process.api.model.node;

import cn.ideabuffer.process.api.model.Model;
import cn.ideabuffer.process.core.Node;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/06/20
 */
public class NodeModel<R extends Node> extends Model<R> {

    private static final long serialVersionUID = -4090498585897073047L;

    public NodeModel(@NotNull R resource) {
        super(resource);
    }

    private boolean enabled;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public void init() {
        super.init();
        this.enabled = resource.enabled();
    }
}
