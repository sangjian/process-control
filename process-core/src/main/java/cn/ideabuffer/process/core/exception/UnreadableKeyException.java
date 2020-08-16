package cn.ideabuffer.process.core.exception;

/**
 * @author sangjian.sj
 * @date 2020/06/09
 */
public class UnreadableKeyException extends RuntimeException {

    public UnreadableKeyException() {
    }

    public UnreadableKeyException(String message) {
        super(message);
    }

    public UnreadableKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnreadableKeyException(Throwable cause) {
        super(cause);
    }

    public UnreadableKeyException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
