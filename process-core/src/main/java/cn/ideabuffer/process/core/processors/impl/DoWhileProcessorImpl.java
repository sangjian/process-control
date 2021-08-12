package cn.ideabuffer.process.core.processors.impl;

import cn.ideabuffer.process.core.KeyManager;
import cn.ideabuffer.process.core.block.BlockFacade;
import cn.ideabuffer.process.core.block.InnerBlock;
import cn.ideabuffer.process.core.context.*;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.processors.DoWhileProcessor;
import cn.ideabuffer.process.core.rules.Rule;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * @author sangjian.sj
 * @date 2020/05/02
 */
public class DoWhileProcessorImpl extends WhileProcessorImpl implements DoWhileProcessor {

    public DoWhileProcessorImpl(Rule rule, BranchNode branch, KeyManager keyManager) {
        super(rule, branch, keyManager);
    }

    @NotNull
    @Override
    public ProcessStatus process(@NotNull Context context) throws Exception {
        if (getRule() == null) {
            throw new NullPointerException("rule can't be null");
        }
        if (getBranch() == null) {
            return ProcessStatus.proceed();
        }

        InnerBlock whileBlock = new InnerBlock(true, true, context.getBlock());
        ContextWrapper whileContext = Contexts.wrap(context, new BlockFacade(whileBlock), getKeyManager());

        do {
            whileBlock.resetBreak();
            whileBlock.resetContinue();
            ProcessStatus status = getBranch().execute(whileContext);
            if (ProcessStatus.isComplete(status)) {
                return status;
            }
            if (whileBlock.hasContinued()) {
                continue;
            }
            if (whileBlock.hasBroken()) {
                break;
            }
        } while (getRule().match(whileContext));

        return ProcessStatus.proceed();
    }
}
