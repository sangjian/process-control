package cn.ideabuffer.process;

import cn.ideabuffer.process.nodes.*;
import cn.ideabuffer.process.nodes.branch.BranchNode;
import cn.ideabuffer.process.nodes.condition.DoWhileConditionNode;
import cn.ideabuffer.process.nodes.condition.IfConditionNode;
import cn.ideabuffer.process.nodes.condition.WhileConditionNode;
import cn.ideabuffer.process.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public class DefaultProcessInstance<R> extends AbstractExecutableNode implements ProcessInstance<R> {

    private ProcessDefine<R> define;

    private R result = null;

    public DefaultProcessInstance(@NotNull ProcessDefine<R> define) {
        this.define = define;
    }

    @Override
    public ProcessStatus doExecute(Context context) throws Exception {
        Context current = context == null ? new DefaultContext() : context;

        Exception exception = null;

        Node[] nodes = define.getNodes();
        ProcessStatus status = ProcessStatus.PROCEED;
        int i;
        for (i = 0; i < nodes.length; i++) {
            Node node = nodes[i];

            if (!node.enabled()) {
                continue;
            }

            if (node instanceof ExecutableNode) {
                try {
                    Context ctx = current;
                    if (node instanceof ProcessInstance) {
                        ctx = new DefaultContext();
                        ctx.putAll(current);
                    }
                    status = ((ExecutableNode)node).execute(ctx);
                    if (ProcessStatus.isComplete(status)) {
                        break;
                    }
                } catch (Exception e) {
                    exception = e;
                    break;
                }
            }

        }

        if (i >= nodes.length) {
            BaseNode<R> baseNode = define.getBaseNode();
            if(baseNode != null) {
                result = define.getBaseNode().invoke(current);
            }
            i--;
        }

        if (exception != null && !postProcess(i, current, exception)) {
            throw exception;
        }

        return status;
    }

    private boolean postProcess(int i, Context context, Exception exception) {
        boolean processed = false;
        Node[] nodes = define.getNodes();
        for (; i >= 0; i--) {
            Node node = nodes[i];
            if (!node.enabled()) {
                continue;
            }
            if (node instanceof PostProcessor) {
                try {
                    boolean result = ((PostProcessor)node).postProcess(context, exception);
                    if (result) {
                        processed = true;
                    }
                } catch (Exception e) {
                    // do something...
                }
            }
        }
        return processed;
    }

    @Override
    public boolean enabled() {
        return true;
    }

    @Override
    public ProcessDefine<R> getProcessDefine() {
        return define;
    }

    @Override
    public R getResult() {
        return result;
    }
}