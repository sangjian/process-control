package cn.ideabuffer.process.api.model.processor;

import cn.ideabuffer.process.api.model.ModelBuilderFactory;
import cn.ideabuffer.process.api.model.builder.ExecutableNodeModelBuilder;
import cn.ideabuffer.process.api.model.node.ExecutableNodeModel;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.processors.BranchProcessor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/06/22
 */
public class BranchProcessorModel<R extends BranchProcessor> extends ProcessorModel<R> {
    private static final long serialVersionUID = 1656596845938118061L;

    private int nodeCount;
    private List<ExecutableNodeModel> nodeModels;

    public BranchProcessorModel(@NotNull R processor) {
        super(processor);
    }

    public int getNodeCount() {
        return nodeCount;
    }

    public void setNodeCount(int nodeCount) {
        this.nodeCount = nodeCount;
    }

    public List<ExecutableNodeModel> getNodeModels() {
        return nodeModels;
    }

    public void setNodeModels(List<ExecutableNodeModel> nodeModels) {
        this.nodeModels = nodeModels;
    }

    @Override
    public void init() {
        super.init();
        this.nodeCount = resource.getNodes() == null ? 0 : resource.getNodes().size();
        nodeModels = new ArrayList<>(nodeCount);
        List<ExecutableNode<?, ?>> nodes = resource.getNodes();
        if (nodes == null || nodes.isEmpty()) {
            return;
        }
        nodes.forEach(node -> {
            ExecutableNodeModelBuilder<ExecutableNode> builder = ModelBuilderFactory.getInstance().getModelBuilder(node);
            if (builder != null) {
                nodeModels.add(builder.build(node));
            }
        });
    }
}
