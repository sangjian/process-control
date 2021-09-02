package cn.ideabuffer.process.core.processors.wrapper.proxy;

import cn.ideabuffer.process.core.KeyManager;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.processors.WhileProcessor;
import cn.ideabuffer.process.core.processors.wrapper.StatusWrapperHandler;
import cn.ideabuffer.process.core.rules.Rule;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2021/06/17
 */
public class WhileProcessorProxy extends AbstractProcessorProxy<WhileProcessor, ProcessStatus>
    implements WhileProcessor {

    public WhileProcessorProxy(@NotNull WhileProcessor target, @NotNull StatusWrapperHandler handler) {
        super(target, handler);
    }

    public static WhileProcessor wrap(@NotNull WhileProcessor target, List<StatusWrapperHandler> handlers) {
        if (handlers == null || handlers.isEmpty()) {
            return target;
        }
        WhileProcessor wrapped = target;
        for (int i = handlers.size() - 1; i >= 0; i--) {
            wrapped = new WhileProcessorProxy(wrapped, handlers.get(i));
        }
        return wrapped;
    }

    @Override
    public Rule getRule() {return getTarget().getRule();}

    @Override
    public void setRule(Rule rule) {
        getTarget().setRule(rule);
    }

    @Override
    public BranchNode getBranch() {return getTarget().getBranch();}

    @Override
    public void setBranch(BranchNode branch) {
        getTarget().setBranch(branch);
    }

    public void setKeyManager(KeyManager keyManager) {
        getTarget().setKeyManager(keyManager);
    }

    public KeyManager getKeyManager() {
        return getTarget().getKeyManager();
    }
}
