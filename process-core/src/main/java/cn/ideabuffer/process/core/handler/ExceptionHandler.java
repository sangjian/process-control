package cn.ideabuffer.process.core.handler;

/**
 * 异常处理器
 *
 * @author sangjian.sj
 * @date 2020/03/07
 */
public interface ExceptionHandler {

    /**
     * 处理异常
     *
     * @param t 异常对象
     * @return
     */
    void handle(Throwable t);

}
