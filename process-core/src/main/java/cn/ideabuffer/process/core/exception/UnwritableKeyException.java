package cn.ideabuffer.process.core.exception;

/**
 * @author sangjian.sj
 * @date 2020/06/09
 */
public class UnwritableKeyException extends RuntimeException {

    public UnwritableKeyException() {
    }

    public UnwritableKeyException(String message) {
        super(message);
    }

    public UnwritableKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnwritableKeyException(Throwable cause) {
        super(cause);
    }

    public UnwritableKeyException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
