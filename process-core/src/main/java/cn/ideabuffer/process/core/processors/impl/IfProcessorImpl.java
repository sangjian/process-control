package cn.ideabuffer.process.core.processors.impl;

import cn.ideabuffer.process.core.KeyManager;
import cn.ideabuffer.process.core.LifecycleManager;
import cn.ideabuffer.process.core.block.BlockFacade;
import cn.ideabuffer.process.core.block.InnerBlock;
import cn.ideabuffer.process.core.context.*;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.processors.IfProcessor;
import cn.ideabuffer.process.core.rules.Rule;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/05/02
 */
public class IfProcessorImpl implements IfProcessor {

    private Rule rule;
    private BranchNode trueBranch;
    private BranchNode falseBranch;
    private KeyManager keyManager;

    public IfProcessorImpl(@NotNull Rule rule, @NotNull BranchNode trueBranch, BranchNode falseBranch) {
        this(rule, trueBranch, falseBranch, null);
    }

    public IfProcessorImpl(@NotNull Rule rule, @NotNull BranchNode trueBranch, BranchNode falseBranch, KeyManager keyManager) {
        this.rule = rule;
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
        this.keyManager = keyManager;
    }

    @Override
    public Rule getRule() {
        return this.rule;
    }

    @Override
    public void setRule(Rule rule) {
        this.rule = rule;
    }

    @Override
    public BranchNode getTrueBranch() {
        return this.trueBranch;
    }

    @Override
    public void setTrueBranch(BranchNode trueBranch) {
        this.trueBranch = trueBranch;
    }

    @Override
    public BranchNode getFalseBranch() {
        return this.falseBranch;
    }

    @Override
    public void setFalseBranch(BranchNode falseBranch) {
        this.falseBranch = falseBranch;
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
        if (rule == null) {
            throw new NullPointerException("rule can't be null");
        }
        InnerBlock ifBlock = new InnerBlock(context.getBlock());
        ContextWrapper contextWrapper = Contexts.wrap(context, new BlockFacade(ifBlock), keyManager);
        BranchNode branch = rule.match(contextWrapper) ? trueBranch : falseBranch;
        if (branch == null) {
            return ProcessStatus.proceed();
        }
        return branch.execute(contextWrapper);
    }

    @Override
    public void initialize() {
        if (trueBranch != null) {
            LifecycleManager.initialize(trueBranch);
        }
        if (falseBranch != null) {
            LifecycleManager.initialize(falseBranch);
        }
    }

    @Override
    public void destroy() {
        if (trueBranch != null) {
            LifecycleManager.destroy(trueBranch);
        }
        if (falseBranch != null) {
            LifecycleManager.destroy(falseBranch);
        }
    }
}
