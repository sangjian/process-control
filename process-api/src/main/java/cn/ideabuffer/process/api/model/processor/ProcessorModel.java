package cn.ideabuffer.process.api.model.processor;

import cn.ideabuffer.process.api.model.Model;
import cn.ideabuffer.process.core.Processor;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/06/20
 */
public class ProcessorModel<R extends Processor> extends Model<R> {

    private static final long serialVersionUID = 2924440843573465231L;

    public ProcessorModel(@NotNull R processor) {
        super(processor);
    }
}
