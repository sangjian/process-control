package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.BooleanSupplier;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public abstract class AbstractNode implements Node {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 默认开启
     */
    private BooleanSupplier enableSupplier = () -> true;

    @Override
    public boolean enabled() {
        if (enableSupplier != null) {
            return enableSupplier.getAsBoolean();
        }
        return false;
    }

    @Override
    public void setEnabled(boolean enable) {
        this.enableSupplier = () -> enable;
    }

    @Override
    public void setEnabled(BooleanSupplier supplier) {
        this.enableSupplier = supplier;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void initialize() {

    }
}
