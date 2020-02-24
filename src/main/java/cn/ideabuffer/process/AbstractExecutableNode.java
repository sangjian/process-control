package cn.ideabuffer.process;

import java.util.concurrent.ExecutorService;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public abstract class AbstractExecutableNode extends AbstractNode implements ExecutableNode {

    public AbstractExecutableNode() {
    }

    public AbstractExecutableNode(String id) {
        super(id);
    }

    protected ExecutorService executor;

    @Override
    public ExecutableNode executeOn(ExecutorService executor) {
        this.executor = executor;
        return this;
    }

    @Override
    public ExecutorService getExecutor() {
        return executor;
    }
}
