package cn.ideabuffer.process.api.model;

import cn.ideabuffer.process.api.model.executor.ExecutorModel;

import java.io.Serializable;

/**
 * @author sangjian.sj
 * @date 2020/06/20
 */
public class ExecuteModel implements Serializable {

    private static final long serialVersionUID = 4938880460225168507L;

    private boolean parallel;

    private ExecutorModel executorModel;

    public boolean isParallel() {
        return parallel;
    }

    public void setParallel(boolean parallel) {
        this.parallel = parallel;
    }

    public ExecutorModel getExecutorModel() {
        return executorModel;
    }

    public void setExecutorModel(ExecutorModel executorModel) {
        this.executorModel = executorModel;
    }
}
