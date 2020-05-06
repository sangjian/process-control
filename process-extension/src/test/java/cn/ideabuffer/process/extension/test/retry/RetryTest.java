package cn.ideabuffer.process.extension.test.retry;

import cn.ideabuffer.process.core.DefaultProcessDefinition;
import cn.ideabuffer.process.core.ProcessDefinition;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.extension.retry.ResultPredicates;
import cn.ideabuffer.process.extension.retry.nodes.RetryableNode;
import cn.ideabuffer.process.extension.retry.nodes.builders.RetryBuilder;
import cn.ideabuffer.process.extension.test.retry.processors.TestRetryProcessor;
import com.github.rholder.retry.*;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author sangjian.sj
 * @date 2020/05/06
 */
public class RetryTest {

    @Test
    public void testRetry() throws Exception {
        ProcessDefinition<String> definition = new DefaultProcessDefinition<>();
        Retryer<String> retryer = RetryerBuilder.<String>newBuilder()
            .retryIfResult(ResultPredicates.IS_EMPTY_STRING_PREDICATE)
            .retryIfException()
            .withWaitStrategy(WaitStrategies.randomWait(1L, TimeUnit.SECONDS))
            .withStopStrategy(StopStrategies.stopAfterAttempt(5))
            .withRetryListener(new RetryListener() {
                @Override
                public <V> void onRetry(Attempt<V> attempt) {
                    try {
                        System.out.println(attempt.get());
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            })
            .build();
        RetryableNode<String> retryableNode = RetryBuilder.<String>newBuilder().retryBy(retryer).by(
            new TestRetryProcessor()).build();
        retryableNode.registerProcessor(new TestRetryProcessor());
        definition.addProcessNodes(retryableNode);

        definition.newInstance().execute(Contexts.newContext());
        Thread.sleep(10000);
    }

}
