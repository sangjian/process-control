package cn.ideabuffer.process.api.model.processor;

import cn.ideabuffer.process.api.model.Model;
import cn.ideabuffer.process.api.model.ModelBuilderFactory;
import cn.ideabuffer.process.api.model.builder.BranchNodeModelBuilder;
import cn.ideabuffer.process.api.model.builder.ModelBuilder;
import cn.ideabuffer.process.api.model.node.BranchNodeModel;
import cn.ideabuffer.process.core.nodes.TryCatchFinallyNode;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.processors.TryCatchFinallyProcessor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/06/22
 */
public class TryCatchFinallyProcessorModel<R extends TryCatchFinallyProcessor> extends ProcessorModel<R> {
    private static final long serialVersionUID = 1656596845938118061L;

    private BranchNodeModel tryBranchModel;
    private List<CatchMapperModel> catchMapperModels;
    private BranchNodeModel finallyBranchModel;

    public TryCatchFinallyProcessorModel(@NotNull R processor) {
        super(processor);
    }

    public BranchNodeModel getTryBranchModel() {
        return tryBranchModel;
    }

    public void setTryBranchModel(BranchNodeModel tryBranchModel) {
        this.tryBranchModel = tryBranchModel;
    }

    public List<CatchMapperModel> getCatchMapperModels() {
        return catchMapperModels;
    }

    public void setCatchMapperModels(
        List<CatchMapperModel> catchMapperModels) {
        this.catchMapperModels = catchMapperModels;
    }

    public BranchNodeModel getFinallyBranchModel() {
        return finallyBranchModel;
    }

    public void setFinallyBranchModel(BranchNodeModel finallyBranchModel) {
        this.finallyBranchModel = finallyBranchModel;
    }

    @Override
    public void init() {
        super.init();
        ModelBuilderFactory factory = ModelBuilderFactory.getInstance();
        BranchNode tryBranch = resource.getTryBranch();
        BranchNodeModelBuilder<BranchNode> tryBranchModelBuilder = factory.getModelBuilder(tryBranch);
        if (tryBranchModelBuilder != null) {
            this.tryBranchModel = tryBranchModelBuilder.build(tryBranch);
        }

        List<TryCatchFinallyNode.CatchMapper> catchMappers = resource.getCatchMapperList();
        if (catchMappers != null && !catchMappers.isEmpty()) {
            catchMapperModels = new ArrayList<>(catchMappers.size());
            catchMappers.forEach(catchMapper -> {
                CatchMapperModelBuilder builder = factory.getModelBuilder(catchMapper);
                if (builder != null) {
                    catchMapperModels.add(builder.build(catchMapper));
                }
            });
        }

        BranchNode finallyBranch = resource.getFinallyBranch();
        BranchNodeModelBuilder<BranchNode> finallyBranchModelBuilder = factory.getModelBuilder(finallyBranch);
        if (finallyBranchModelBuilder != null) {
            this.finallyBranchModel = finallyBranchModelBuilder.build(finallyBranch);
        }
    }

    public static class CatchMapperModel extends Model<TryCatchFinallyNode.CatchMapper> {

        private static final long serialVersionUID = 2184647179857736021L;
        private String expClassName;

        private BranchNodeModel branchNodeModel;

        public CatchMapperModel(@NotNull TryCatchFinallyNode.CatchMapper resource) {
            super(resource);
        }

        public String getExpClassName() {
            return expClassName;
        }

        public void setExpClassName(String expClassName) {
            this.expClassName = expClassName;
        }

        public BranchNodeModel getBranchNodeModel() {
            return branchNodeModel;
        }

        public void setBranchNodeModel(BranchNodeModel branchNodeModel) {
            this.branchNodeModel = branchNodeModel;
        }

        @Override
        protected void init() {
            super.init();
            Class<?> expClass = resource.getExceptionClass();
            if (expClass != null) {
                this.expClassName = expClass.getName();
            }
            BranchNode branchNode = resource.getBranchNode();
            if (branchNode != null) {
                this.branchNodeModel = ModelBuilderFactory.getInstance().<BranchNodeModelBuilder<BranchNode>>getModelBuilder(branchNode).build(branchNode);
            }
        }
    }

    public static class CatchMapperModelBuilder extends ModelBuilder<TryCatchFinallyNode.CatchMapper> {
        @Override
        public CatchMapperModel build(TryCatchFinallyNode.CatchMapper resource) {
            return new CatchMapperModel(resource);
        }
    }
}
