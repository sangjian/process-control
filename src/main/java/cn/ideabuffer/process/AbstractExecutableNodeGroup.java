package cn.ideabuffer.process;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public abstract class AbstractExecutableNodeGroup extends AbstractExecutableNode implements ExecutableNodeGroup {

    private ExecutableNode[] nodes = new ExecutableNode[0];

    public AbstractExecutableNodeGroup() {
        this(null, null);
    }

    public AbstractExecutableNodeGroup(ExecutableNode... nodes) {
        this(null, nodes);
    }

    public AbstractExecutableNodeGroup(String id, ExecutableNode... nodes) {
        super(id);
        if(nodes != null) {
            this.nodes = nodes;
        }
    }



    @Override
    public ExecutableNode[] getNodes() {
        return nodes;
    }

    @Override
    public ExecutableNodeGroup addNode(ExecutableNode node) {
        if(node == null) {
            throw  new NullPointerException();
        }
        ExecutableNode[] newArr = new ExecutableNode[nodes.length + 1];
        System.arraycopy(nodes, 0, newArr, 0, nodes.length);
        newArr[nodes.length] = node;
        this.nodes = newArr;
        return this;
    }

    @Override
    public boolean execute(Context context) throws Exception {
        List<Future<Boolean>> futureList = new ArrayList<>();
        boolean parallel = executor != null;
        boolean stop = false;
        try {
            for (ExecutableNode node : nodes) {
                if(parallel) {
                    Future<Boolean> future = doExecuteAsync(node, context);
                    if(future != null) {
                        futureList.add(future);
                    }
                } else {
                    if(doExecute(node, context)) {
                        stop = true;
                    }
                }
            }
            if(parallel) {
                for (Future<Boolean> future : futureList) {
                    stop = future.get();
                    if (stop) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }

        return stop;
    }

    protected boolean doExecute(ExecutableNode node, Context context) throws Exception {
        return node.execute(context);
    }

    protected Future<Boolean> doExecuteAsync(ExecutableNode node, Context context) {
        NodeTask task = new NodeTask(node, context);
        return executor.submit(task);
    }

    class NodeTask implements Callable<Boolean>{
        ExecutableNode node;
        Context context;

        NodeTask(ExecutableNode node, Context context) {
            this.node = node;
            this.context = context;
        }

        @Override
        public Boolean call() throws Exception {
            return node.execute(context);
        }
    }
}
