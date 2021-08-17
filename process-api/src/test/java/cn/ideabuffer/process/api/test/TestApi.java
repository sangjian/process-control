package cn.ideabuffer.process.api.test;

import cn.ideabuffer.process.api.model.ModelBuilderFactory;
import cn.ideabuffer.process.api.model.builder.ExecutableNodeModelBuilder;
import cn.ideabuffer.process.api.model.builder.ProcessDefinitionModelBuilder;
import cn.ideabuffer.process.api.model.node.ExecutableNodeModel;
import cn.ideabuffer.process.api.model.node.ProcessDefinitionModel;
import cn.ideabuffer.process.core.DefaultProcessDefinition;
import cn.ideabuffer.process.core.ProcessDefinition;
import cn.ideabuffer.process.core.ProcessDefinitionBuilder;
import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.Nodes;
import cn.ideabuffer.process.core.nodes.ProcessNode;
import cn.ideabuffer.process.core.nodes.TryCatchFinallyNode;
import cn.ideabuffer.process.core.nodes.builder.*;
import cn.ideabuffer.process.core.nodes.condition.DoWhileConditionNode;
import cn.ideabuffer.process.core.nodes.condition.IfConditionNode;
import cn.ideabuffer.process.core.nodes.condition.WhileConditionNode;
import cn.ideabuffer.process.core.status.ProcessStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author sangjian.sj
 * @date 2020/06/20
 */
public class TestApi {

    @Test
    public void test() throws JsonProcessingException {

        Executor executor = Executors.newFixedThreadPool(2);

        ProcessNode<ProcessStatus> node = ProcessNodeBuilder.<ProcessStatus>newBuilder().addListeners(new TestProcessListener1(), new TestProcessListener2()).parallel(executor).by(
            context -> null).processOn(context -> false).build();

        ExecutableNodeModel<ProcessNode<ProcessStatus>> model
            = new ExecutableNodeModelBuilder<ProcessNode<ProcessStatus>>().build(node);

        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(model);
        System.out.println(s);
    }

    @Test
    public void testSub() throws JsonProcessingException {

        Executor executor = Executors.newFixedThreadPool(2);

        ProcessNode<ProcessStatus> node = ProcessNodeBuilder.<ProcessStatus>newBuilder().addListeners(new TestProcessListener1(), new TestProcessListener2()).parallel(executor).by(
            context -> null).processOn(context -> false).build();

        IfConditionNode ifConditionNode = IfNodeBuilder.newBuilder()
            .processOn(context -> true)
            .then(Nodes.newBranch(Nodes.newProcessNode(context -> null)))
            .otherwise(Nodes.newBranch(Nodes.newProcessNode(context -> null)))
            .build();

        TryCatchFinallyNode tryCatchFinallyNode = TryCatchFinallyNodeBuilder.newBuilder().tryOn(Nodes.newProcessNode(context -> null))
            .catchOn(NullPointerException.class, Nodes.newProcessNode(context -> null))
            .catchOn(IllegalArgumentException.class, Nodes.newProcessNode(context -> null))
            .doFinally(Nodes.newProcessNode(context -> null))
            .build();

        WhileConditionNode whileConditionNode = WhileNodeBuilder.newBuilder().processOn(ctx -> true).then(Nodes.newProcessNode(context -> null)).build();
        DoWhileConditionNode doWhileConditionNode = DoWhileNodeBuilder.newBuilder().processOn(ctx -> true).then((Nodes.newProcessNode(context -> null))).build();

//        ProcessDefinition<ProcessStatus> definition = new DefaultProcessDefinition<>();
        ProcessDefinition<ProcessStatus> definition = ProcessDefinitionBuilder.<ProcessStatus>newBuilder()
            .addBranchNode(BranchNodeBuilder.newBuilder()
            .addNodes(node).build()).addIf(ifConditionNode)
            .addProcessNodes(tryCatchFinallyNode)
            .addWhile(whileConditionNode)
            .addDoWhile(doWhileConditionNode)
            .build();

        ProcessDefinitionModel<ProcessDefinition> model = ModelBuilderFactory
            .getInstance().<ProcessDefinitionModelBuilder<ProcessDefinition>>getModelBuilder(definition).build(
                definition);

        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(model);
        System.out.println(s);
    }

}
