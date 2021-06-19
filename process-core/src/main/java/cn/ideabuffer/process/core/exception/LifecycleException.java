package cn.ideabuffer.process.core.exception;

/**
 * @author sangjian.sj
 * @date 2020/04/06
 */
public class LifecycleException extends RuntimeException {

    private static final long serialVersionUID = 3150430169711976695L;

    public LifecycleException() {
    }

    public LifecycleException(String message) {
        super(message);
    }

    public LifecycleException(String message, Throwable cause) {
        super(message, cause);
    }

    public LifecycleException(Throwable cause) {
        super(cause);
    }

    public LifecycleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
