package cn.ideabuffer.process.extension.test.retry;

import cn.ideabuffer.process.core.DefaultProcessDefinition;
import cn.ideabuffer.process.core.ProcessDefinition;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.extension.retry.nodes.RetryableNode;
import cn.ideabuffer.process.extension.retry.nodes.builders.RetryBuilder;
import cn.ideabuffer.process.extension.test.retry.processors.TestRetryProcessor;
import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import org.junit.Test;

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
            .retryIfResult("hello"::equals)
            .retryIfException()
            .withWaitStrategy(WaitStrategies.randomWait(1L, TimeUnit.SECONDS))
            .withStopStrategy(StopStrategies.stopAfterAttempt(5))
            .build();
        RetryableNode<String> retryableNode = RetryBuilder.<String>newBuilder().retryBy(retryer).by(
            new TestRetryProcessor()).build();
        retryableNode.registerProcessor(new TestRetryProcessor());
        definition.addProcessNodes(retryableNode);

        definition.newInstance().execute(Contexts.newContext());
        Thread.sleep(10000);
    }

}
