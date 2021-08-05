package cn.ideabuffer.process.api.model.executor;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author sangjian.sj
 * @date 2020/06/20
 */
public class ExecutorModel implements Serializable {

    private static final long serialVersionUID = -7396165599337428106L;

    public static ExecutorModel from(Executor executor) {
        if (executor == null) {
            return null;
        }
        if (executor instanceof ThreadPoolExecutor) {
            return new ThreadPoolExecutorModel((ThreadPoolExecutor)executor);
        }
        return null;
    }

    class BlockingQueueModel implements Serializable {
        private static final long serialVersionUID = 2181319050723525711L;
        private String className;
        private int size;

        public BlockingQueueModel(@NotNull BlockingQueue queue) {
            this.className = queue.getClass().getName();
            this.size = queue.size();
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }

    class RejectPolicyModel implements Serializable {
        private static final long serialVersionUID = -4317760947473989151L;
        private String className;

        public RejectPolicyModel(@NotNull RejectedExecutionHandler handler) {
            this.className = handler.getClass().getName();
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }
    }

}
