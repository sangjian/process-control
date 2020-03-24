package cn.ideabuffer.process.strategy;

import cn.ideabuffer.process.status.ProcessStatus;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author sangjian.sj
 * @date 2020/02/25
 */
public class AtLeastOneProceededStrategy implements ProceedStrategy {

    @Override
    public boolean proceed(List<CompletableFuture<ProcessStatus>> futures) throws Exception {
        if (futures == null || futures.isEmpty()) {
            return true;
        }

        // 执行结果队列
        BlockingQueue<ProcessStatus> queue = new LinkedBlockingQueue<>(futures.size());
        for (CompletableFuture<ProcessStatus> future : futures) {
            // 执行完成回调
            future.whenComplete((b, t) -> {
                if (t != null) {
                    // 有异常
                    queue.offer(ProcessStatus.COMPLETE);
                } else {
                    // 执行结果入队
                    queue.offer(b);
                }
            });
        }

        for (int i = 0; i < queue.size(); i++) {
            if (ProcessStatus.isProceed(queue.take())) {
                return true;
            }
        }

        return false;
    }
}
