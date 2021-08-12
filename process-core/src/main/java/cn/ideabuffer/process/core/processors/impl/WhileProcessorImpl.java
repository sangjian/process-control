package cn.ideabuffer.process.core.processors.impl;

import cn.ideabuffer.process.core.KeyManager;
import cn.ideabuffer.process.core.LifecycleManager;
import cn.ideabuffer.process.core.block.BlockFacade;
import cn.ideabuffer.process.core.block.InnerBlock;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.ContextWrapper;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.processors.WhileProcessor;
import cn.ideabuffer.process.core.rules.Rule;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/05/02
 */
public class WhileProcessorImpl implements WhileProcessor {

    private Rule rule;
    private BranchNode branch;
    private KeyManager keyManager;

    public WhileProcessorImpl(Rule rule, BranchNode branch, KeyManager keyManager) {
        this.rule = rule;
        this.branch = branch;
        this.keyManager = keyManager;
    }

    @Override
    public Rule getRule() {
        return rule;
    }

    @Override
    public void setRule(Rule rule) {
        this.rule = rule;
    }

    @Override
    public BranchNode getBranch() {
        return branch;
    }

    @Override
    public void setBranch(BranchNode branch) {
        this.branch = branch;
    }

    @Override
    public void setKeyManager(KeyManager keyManager) {
        this.keyManager = keyManager;
    }

    @Override
    public KeyManager getKeyManager() {
        return this.keyManager;
    }

    @NotNull
    @Override
    public ProcessStatus process(@NotNull Context context) throws Exception {
        if (getRule() == null) {
            throw new NullPointerException("rule can't be null");
        }
        if (branch == null) {
            return ProcessStatus.proceed();
        }

        InnerBlock whileBlock = new InnerBlock(true, true, context.getBlock());
        ContextWrapper whileContext = Contexts.wrap(context, new BlockFacade(whileBlock), keyManager);

        while (getRule().match(whileContext)) {
            ProcessStatus status = branch.execute(whileContext);
            if (ProcessStatus.isComplete(status)) {
                return status;
            }
            if (whileBlock.hasBroken()) {
                break;
            }

            whileBlock.resetBreak();
            whileBlock.resetContinue();
        }

        return ProcessStatus.proceed();
    }

    @Override
    public void initialize() {
        if (branch == null) {
            return;
        }
        LifecycleManager.initialize(branch);
    }

    @Override
    public void destroy() {
        if (branch == null) {
            return;
        }
        LifecycleManager.destroy(branch);
    }
}
