package cn.ideabuffer.process.extension.retry.nodes;

import cn.ideabuffer.process.core.ProcessListener;
import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.rule.Rule;
import com.github.rholder.retry.Retryer;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/05/06
 */
public class DefaultRetryNode<R> extends AbstractRetryableNode<R> {

    public DefaultRetryNode() {
        super(null);
    }

    public DefaultRetryNode(Retryer<R> retryer) {
        super(retryer);
    }

    public DefaultRetryNode(boolean parallel, Retryer<R> retryer) {
        super(parallel, retryer);
    }

    public DefaultRetryNode(Rule rule, Retryer<R> retryer) {
        super(rule, retryer);
    }

    public DefaultRetryNode(boolean parallel, Executor executor,
        Retryer<R> retryer) {
        super(parallel, executor, retryer);
    }

    public DefaultRetryNode(boolean parallel, Rule rule, Executor executor,
        Retryer<R> retryer) {
        super(parallel, rule, executor, retryer);
    }

    public DefaultRetryNode(boolean parallel, Rule rule, Executor executor,
        List<ProcessListener<R>> listeners, Processor<R> processor,
        Retryer<R> retryer) {
        super(parallel, rule, executor, listeners, processor, retryer);
    }
}
