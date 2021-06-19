package cn.ideabuffer.process.core.processors.wrapper.proxy;

import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.context.KeyMapper;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.processors.WhileProcessor;
import cn.ideabuffer.process.core.processors.wrapper.StatusWrapperHandler;
import cn.ideabuffer.process.core.rule.Rule;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

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
    public BranchNode getBranch() {return getTarget().getBranch();}

    @Override
    public KeyMapper getKeyMapper() {return getTarget().getKeyMapper();}

    @Override
    public void setKeyMapper(KeyMapper keyMapper) {getTarget().setKeyMapper(keyMapper);}

    @Override
    public Set<Key<?>> getReadableKeys() {return getTarget().getReadableKeys();}

    @Override
    public void setReadableKeys(Set<Key<?>> readableKeys) {getTarget().setReadableKeys(readableKeys);}

    @Override
    public Set<Key<?>> getWritableKeys() {return getTarget().getWritableKeys();}

    @Override
    public void setWritableKeys(Set<Key<?>> writableKeys) {getTarget().setWritableKeys(writableKeys);}
}
