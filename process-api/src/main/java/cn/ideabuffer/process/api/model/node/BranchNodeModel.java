package cn.ideabuffer.process.api.model.node;

import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/06/22
 */
public class BranchNodeModel<R extends BranchNode> extends ExecutableNodeModel<R> {

    private static final long serialVersionUID = 5994716736654605209L;

    public BranchNodeModel(@NotNull R node) {
        super(node);
    }
}
