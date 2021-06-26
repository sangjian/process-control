package cn.ideabuffer.process.api.model.node;

import cn.ideabuffer.process.api.model.*;
import cn.ideabuffer.process.api.model.builder.ProcessListenerModelBuilder;
import cn.ideabuffer.process.api.model.builder.ProcessorModelBuilder;
import cn.ideabuffer.process.api.model.builder.RuleModelBuilder;
import cn.ideabuffer.process.api.model.executor.ExecutorModel;
import cn.ideabuffer.process.api.model.listener.ProcessListenerModel;
import cn.ideabuffer.process.api.model.processor.ProcessorModel;
import cn.ideabuffer.process.core.ProcessListener;
import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.rules.Rule;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/06/20
 */
public class ExecutableNodeModel<R extends ExecutableNode> extends NodeModel<R> {

    private static final long serialVersionUID = -5937473465701980282L;

    private ProcessorModel processorModel;

    private List<ProcessListenerModel> processListenerModels;

    private RuleModel ruleModel;

    private KeyMapperModel keyMapperModel;

    private ExecuteModel executeModel;

    public ExecutableNodeModel(@NotNull R node) {
        super(node);
    }

    public ProcessorModel getProcessorModel() {
        return processorModel;
    }

    public void setProcessorModel(@Nullable ProcessorModel processorModel) {
        this.processorModel = processorModel;
    }

    public List<ProcessListenerModel> getProcessListenerModels() {
        return processListenerModels;
    }

    public void setProcessListenerModels(
        @Nullable List<ProcessListenerModel> processListenerModels) {
        this.processListenerModels = processListenerModels;
    }

    public RuleModel getRuleModel() {
        return ruleModel;
    }

    public void setRuleModel(@Nullable RuleModel ruleModel) {
        this.ruleModel = ruleModel;
    }

    public KeyMapperModel getKeyMapperModel() {
        return keyMapperModel;
    }

    public void setKeyMapperModel(KeyMapperModel keyMapperModel) {
        this.keyMapperModel = keyMapperModel;
    }

    public ExecuteModel getExecuteModel() {
        return executeModel;
    }

    public void setExecuteModel(ExecuteModel executeModel) {
        this.executeModel = executeModel;
    }

    @Override
    public void init() {
        super.init();

        ModelBuilderFactory factory = ModelBuilderFactory.getInstance();

        ProcessorModelBuilder<Processor> processorModelBuilder = factory.getModelBuilder(resource.getProcessor());
        if (processorModelBuilder != null) {
            this.setProcessorModel(processorModelBuilder.build(resource.getProcessor()));
        }
        List<ProcessListener> processListeners = resource.getListeners();
        if (processListeners != null && !processListeners.isEmpty()) {
            processListenerModels = new ArrayList<>(processListeners.size());
            processListeners.forEach(listener -> {
                ProcessListenerModelBuilder<ProcessListener> processListenerModelBuilder = factory.getModelBuilder(listener);
                if (processListenerModelBuilder != null) {
                    processListenerModels.add(processListenerModelBuilder.build(listener));
                }
            });
        }
        RuleModelBuilder<Rule> ruleModelBuilder = factory.getModelBuilder(resource.getRule());
        if (ruleModelBuilder != null) {
            this.ruleModel = ruleModelBuilder.build(resource.getRule());
        }

        ExecuteModel executeModel = new ExecuteModel();
        executeModel.setParallel(resource.isParallel());
        executeModel.setExecutorModel(ExecutorModel.from(resource.getExecutor()));

        this.executeModel = executeModel;
    }
}
