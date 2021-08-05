package cn.ideabuffer.process.api.model.listener;

import cn.ideabuffer.process.api.model.Model;
import cn.ideabuffer.process.core.ProcessListener;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/06/20
 */
public class ProcessListenerModel<R extends ProcessListener> extends Model<R> {

    private static final long serialVersionUID = 158391458238883852L;

    public ProcessListenerModel(@NotNull R listener) {
        super(listener);
    }

}
