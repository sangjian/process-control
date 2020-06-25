package cn.ideabuffer.process.api.model.executor;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author sangjian.sj
 * @date 2020/06/20
 */
public class ThreadPoolExecutorModel extends ExecutorModel {

    private static final long serialVersionUID = -415065561971976337L;

    public ThreadPoolExecutorModel(@NotNull ThreadPoolExecutor executor) {
        this.largestPoolSize = executor.getLargestPoolSize();
        this.completedTaskCount = executor.getCompletedTaskCount();
        this.keepAliveTime = executor.getKeepAliveTime(TimeUnit.MILLISECONDS);
        this.corePoolSize = executor.getCorePoolSize();
        this.maximumPoolSize = executor.getMaximumPoolSize();
        this.queueModel = new BlockingQueueModel(executor.getQueue());
        this.rejectPolicyModel = new RejectPolicyModel(executor.getRejectedExecutionHandler());

    }

    /**
     * Tracks largest attained pool size. Accessed only under
     * mainLock.
     */
    private int largestPoolSize;

    /**
     * Counter for completed tasks. Updated only on termination of
     * worker threads. Accessed only under mainLock.
     */
    private long completedTaskCount;

    /**
     * Timeout in nanoseconds for idle threads waiting for work.
     * Threads use this timeout when there are more than corePoolSize
     * present or if allowCoreThreadTimeOut. Otherwise they wait
     * forever for new work.
     */
    private long keepAliveTime;

    /**
     * If false (default), core threads stay alive even when idle.
     * If true, core threads use keepAliveTime to time out waiting
     * for work.
     */
    private boolean allowCoreThreadTimeOut;

    /**
     * Core pool size is the minimum number of workers to keep alive
     * (and not allow to time out etc) unless allowCoreThreadTimeOut
     * is set, in which case the minimum is zero.
     */
    private int corePoolSize;

    /**
     * Maximum pool size. Note that the actual maximum is internally
     * bounded by CAPACITY.
     */
    private int maximumPoolSize;

    private BlockingQueueModel queueModel;

    private RejectPolicyModel rejectPolicyModel;

    public int getLargestPoolSize() {
        return largestPoolSize;
    }

    public void setLargestPoolSize(int largestPoolSize) {
        this.largestPoolSize = largestPoolSize;
    }

    public long getCompletedTaskCount() {
        return completedTaskCount;
    }

    public void setCompletedTaskCount(long completedTaskCount) {
        this.completedTaskCount = completedTaskCount;
    }

    public long getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public boolean isAllowCoreThreadTimeOut() {
        return allowCoreThreadTimeOut;
    }

    public void setAllowCoreThreadTimeOut(boolean allowCoreThreadTimeOut) {
        this.allowCoreThreadTimeOut = allowCoreThreadTimeOut;
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public void setMaximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public BlockingQueueModel getQueueModel() {
        return queueModel;
    }

    public void setQueueModel(BlockingQueueModel queueModel) {
        this.queueModel = queueModel;
    }

    public RejectPolicyModel getRejectPolicyModel() {
        return rejectPolicyModel;
    }

    public void setRejectPolicyModel(RejectPolicyModel rejectPolicyModel) {
        this.rejectPolicyModel = rejectPolicyModel;
    }
}
