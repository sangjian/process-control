package cn.ideabuffer.process.api.model.node;

import cn.ideabuffer.process.api.model.Model;
import cn.ideabuffer.process.api.model.ModelBuilderFactory;
import cn.ideabuffer.process.api.model.builder.NodeModelBuilder;
import cn.ideabuffer.process.core.Node;
import cn.ideabuffer.process.core.ProcessDefinition;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/06/25
 */
public class ProcessDefinitionModel<R extends ProcessDefinition> extends Model<ProcessDefinition> {

    private List<Model> nodes;



    public ProcessDefinitionModel(@NotNull R resource) {
        super(resource);
    }

    public List<Model> getNodes() {
        return nodes;
    }

    public void setNodes(List<Model> nodes) {
        this.nodes = nodes;
    }

    @Override
    public void init() {
        super.init();
        Node[] arr = resource.getNodes();
        if (arr.length == 0) {
            return;
        }
        this.nodes = new ArrayList<>(arr.length);
        ModelBuilderFactory factory = ModelBuilderFactory.getInstance();
        for (int i = 0; i < arr.length; i++) {
            nodes.add(factory.<NodeModelBuilder<Node>>getModelBuilder(arr[i]).build(arr[i]));
        }
    }
}
