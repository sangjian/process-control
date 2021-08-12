package cn.ideabuffer.process.core.processors.wrapper.proxy;

import cn.ideabuffer.process.core.KeyManager;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.context.KeyMapper;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.processors.DoWhileProcessor;
import cn.ideabuffer.process.core.processors.wrapper.StatusWrapperHandler;
import cn.ideabuffer.process.core.rules.Rule;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

/**
 * @author sangjian.sj
 * @date 2021/06/17
 */
public class DoWhileProcessorProxy extends AbstractProcessorProxy<DoWhileProcessor, ProcessStatus>
    implements DoWhileProcessor {

    public DoWhileProcessorProxy(@NotNull DoWhileProcessor target, @NotNull StatusWrapperHandler handler) {
        super(target, handler);
    }

    public static DoWhileProcessor wrap(@NotNull DoWhileProcessor target, List<StatusWrapperHandler> handlers) {
        if (handlers == null || handlers.isEmpty()) {
            return target;
        }
        DoWhileProcessor wrapped = target;
        for (int i = handlers.size() - 1; i >= 0; i--) {
            wrapped = new DoWhileProcessorProxy(wrapped, handlers.get(i));
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
