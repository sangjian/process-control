package cn.ideabuffer.process.core.test;

import org.junit.Test;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author sangjian.sj
 * @date 2020/05/14
 */
public class CompletableFutureTest {
    private ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(2);
    @Test
    public void testTimeout() throws ExecutionException, InterruptedException {
        System.out.println("start");
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            throw new RuntimeException("test");
            //System.out.println("hahahhahahahaa");
            //return "hello";
        }).thenApply(s -> {
            System.out.println("in apply");
            return "";
        }).exceptionally(t -> {
            System.out.println("in exceptionally" + t);
            return null;
        });
        CompletableFuture<Void> f = future.acceptEither(failAfter(Duration.ofSeconds(5)), s -> {})
            .exceptionally(throwable -> {
                System.out.println("exceptionally, throwable:" + throwable.getCause().getClass());
                future.completeExceptionally(new TimeoutException("Timeout after "));
                //throwable.printStackTrace();
                return null;
            });
        CompletableFuture.allOf(f).get();
        System.out.println("done");
        //System.out.println(future.get());
        Thread.sleep(12000);
    }

    @Test
    public void testTimeoutAllOf() throws ExecutionException, InterruptedException {
        System.out.println("start");
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("hahahhahahahaa");
            return "hello";
        });
        CompletableFuture<?> timeoutFuture = failAfter(Duration.ofSeconds(10));
        CompletableFuture.allOf(future, timeoutFuture).join();
        //System.out.println(future.get());
        Thread.sleep(10000);
    }

    public <T> CompletableFuture<T> failAfter(Duration duration) {
        final CompletableFuture<T> promise = new CompletableFuture<>();
        scheduler.schedule(() -> {
            System.out.println("in timeout");
            promise.completeExceptionally(new TimeoutException());
        }, duration.toMillis(), MILLISECONDS);
        return promise;
    }

}
