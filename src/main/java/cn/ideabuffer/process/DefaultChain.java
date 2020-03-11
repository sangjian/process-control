package cn.ideabuffer.process;

import cn.ideabuffer.process.handler.ExceptionHandler;
import cn.ideabuffer.process.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.nodes.AggregatableNode;
import cn.ideabuffer.process.nodes.ExecutableNode;
import cn.ideabuffer.process.nodes.NodeGroup;
import cn.ideabuffer.process.nodes.condition.DoWhileConditionNode;
import cn.ideabuffer.process.nodes.condition.IfConditionNode;
import cn.ideabuffer.process.nodes.condition.WhileConditionNode;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public class DefaultChain extends AbstractExecutableNode implements Chain {

    private Node[] nodes = new Node[0];

    private volatile boolean running;

    private Chain addNode(Node node) {
        if (node == null) {
            throw new IllegalArgumentException();
        }
        if (running) {
            throw new IllegalStateException();
        }
        Node[] newArr = new Node[nodes.length + 1];
        System.arraycopy(nodes, 0, newArr, 0, nodes.length);
        newArr[nodes.length] = node;
        nodes = newArr;
        return this;
    }

    @Override
    public Chain addProcessNode(ExecutableNode node) {
        return addNode(node);
    }

    @Override
    public Chain addIf(IfConditionNode node) {
        return addNode(node);
    }

    @Override
    public Chain addWhile(WhileConditionNode node) {
        return addNode(node);
    }

    @Override
    public Chain addDoWhile(DoWhileConditionNode node) {
        return addNode(node);
    }

    @Override
    public Chain addGroup(NodeGroup group) {
        return addNode(group);
    }

    @Override
    public <T> Chain addAggregateNode(AggregatableNode<T> node) {
        return addNode(node);
    }

    @Override
    public boolean doExecute(Context context) throws Exception {

        if (context == null) {
            context = new DefaultContext();
        }

        running = true;
        Exception exception = null;

        boolean stop = false;
        int i;
        for (i = 0; i < nodes.length; i++) {
            Node node = nodes[i];

            if (!node.enabled(context)) {
                continue;
            }

            if (node instanceof ExecutableNode) {
                try {
                    if (((ExecutableNode)node).execute(context)) {
                        stop = true;
                    }
                    if (stop) {
                        break;
                    }
                } catch (Exception e) {
                    exception = e;
                    break;
                }
            }

        }

        if (i >= nodes.length) {
            i--;
        }
        boolean processed = false;
        for (; i >= 0; i--) {
            Node node = nodes[i];
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

        if (exception != null && !processed) {
            throw exception;
        }

        return stop;
    }

    @Override
    public boolean enabled(Context context) {
        return true;
    }

}