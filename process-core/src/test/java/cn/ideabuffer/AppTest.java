package cn.ideabuffer;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() throws InterruptedException {
        System.out.println(null instanceof Object);
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(() -> {
            try {
                Thread.sleep(5000);
                System.out.println("done");
                System.out.println(Thread.currentThread().isInterrupted());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread.sleep(100);
        System.out.println("start shutdown");
        executorService.shutdown();
        System.out.println("after shutdown");
        System.out.println(executorService.isShutdown());
        System.out.println(executorService.isTerminated());
        Thread.sleep(10000);



    }
}
