package cn.ideabuffer.process.api.model.processor;

import cn.ideabuffer.process.core.processors.WhileProcessor;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/06/22
 */
public class DoWhileProcessorModel<R extends WhileProcessor> extends WhileProcessorModel<R> {

    private static final long serialVersionUID = 7517527441917002800L;

    public DoWhileProcessorModel(@NotNull R processor) {
        super(processor);
    }
}
