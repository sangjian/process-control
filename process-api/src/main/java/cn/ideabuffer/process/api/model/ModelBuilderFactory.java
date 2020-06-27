package cn.ideabuffer.process.api.model;

import cn.ideabuffer.process.api.model.builder.*;
import cn.ideabuffer.process.api.model.processor.TryCatchFinallyProcessorModel;
import cn.ideabuffer.process.core.*;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.nodes.TryCatchFinallyNode;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.nodes.condition.DoWhileConditionNode;
import cn.ideabuffer.process.core.nodes.condition.IfConditionNode;
import cn.ideabuffer.process.core.nodes.condition.WhileConditionNode;
import cn.ideabuffer.process.core.processors.*;
import cn.ideabuffer.process.core.rule.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sangjian.sj
 * @date 2020/06/22
 */
public class ModelBuilderFactory {

    private static final ModelBuilderFactory INSTANCE = new ModelBuilderFactory();

    private Map<Class, ModelBuilder> builderMap;

    private ModelBuilderFactory() {
        builderMap = new ConcurrentHashMap<>();
        builderMap.putIfAbsent(Object.class, new ModelBuilder());
        builderMap.putIfAbsent(Node.class, new NodeModelBuilder());
        builderMap.putIfAbsent(ExecutableNode.class, new ExecutableNodeModelBuilder());
        builderMap.putIfAbsent(BranchNode.class, new BranchNodeModelBuilder());
        builderMap.putIfAbsent(Processor.class, new ProcessorModelBuilder());
        builderMap.putIfAbsent(ComplexProcessor.class, new ComplexProcessorModelBuilder());
        builderMap.putIfAbsent(BranchProcessor.class, new BranchProcessorModelBuilder());
        builderMap.putIfAbsent(ProcessListener.class, new ProcessListenerModelBuilder());
        builderMap.putIfAbsent(NodeListener.class, new NodeListenerModelBuilder());
        builderMap.putIfAbsent(Rule.class, new RuleModelBuilder());
        builderMap.putIfAbsent(ProcessDefinition.class, new ProcessDefinitionModelBuilder());

        // 条件节点
        builderMap.putIfAbsent(IfConditionNode.class, new IfConditionNodeModelBuilder());
        builderMap.putIfAbsent(IfProcessor.class, new IfProcessorModelBuilder());

        builderMap.putIfAbsent(WhileConditionNode.class, new WhileConditionNodeModelBuilder());
        builderMap.putIfAbsent(DoWhileConditionNode.class, new DoWhileConditionNodeModelBuilder());
        builderMap.putIfAbsent(WhileProcessor.class, new WhileProcessorModelBuilder());
        builderMap.putIfAbsent(DoWhileProcessor.class, new DoWhileProcessorModelBuilder());

        builderMap.putIfAbsent(TryCatchFinallyProcessor.class, new TryCatchFinallyProcessorModelBuilder());
        builderMap.putIfAbsent(TryCatchFinallyNode.class, new TryCatchFinallyNodeModelBuilder());
        builderMap.putIfAbsent(TryCatchFinallyNode.CatchMapper.class, new TryCatchFinallyProcessorModel.CatchMapperModelBuilder());
    }

    public static ModelBuilderFactory getInstance() {
        return INSTANCE;
    }

    public <M extends ModelBuilder> void register(Class<?> clazz, M builder) {
        register(clazz, builder, false);
    }

    public <M extends ModelBuilder> void register(Class<?> clazz, M builder, boolean force) {
        if (force) {
            builderMap.put(clazz, builder);
        } else {
            builderMap.putIfAbsent(clazz, builder);
        }
    }

    public <M extends ModelBuilder> M getModelBuilder(Object resource) {
        if (resource == null) {
            return null;
        }
        return getModelBuilderByClass(resource.getClass());
    }

    @SuppressWarnings("unchecked")
    @NotNull
    public <M extends ModelBuilder> M getModelBuilderByClass(@NotNull Class<?> clazz) {
        ModelBuilder builder = builderMap.get(clazz);
        if (builder != null) {
            return (M)builder;
        }
        Class<?> parent = clazz.getSuperclass();
        Class<?>[] interfaces = clazz.getInterfaces();
        while ((parent != null && parent != Object.class) || interfaces.length > 0) {
            for (Class<?> anInterface : interfaces) {
                builder = builderMap.get(anInterface);
                if (builder != null) {
                    builderMap.putIfAbsent(clazz, builder);
                    return (M)builder;
                }
            }
            if (parent != Object.class) {
                builder = builderMap.get(parent);
                if (builder != null) {
                    builderMap.putIfAbsent(clazz, builder);
                    return (M)builder;
                }
            }
            if (parent == null) {
                break;
            }
            interfaces = parent.getInterfaces();
            parent = parent.getSuperclass();

        }

        return (M)new ModelBuilder();
    }

}
