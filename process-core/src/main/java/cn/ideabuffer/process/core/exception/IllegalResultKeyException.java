package cn.ideabuffer.process.core.exception;

/**
 * @author sangjian.sj
 * @date 2020/06/09
 */
public class IllegalResultKeyException extends RuntimeException {

    public IllegalResultKeyException() {
    }

    public IllegalResultKeyException(String message) {
        super(message);
    }

    public IllegalResultKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalResultKeyException(Throwable cause) {
        super(cause);
    }

    public IllegalResultKeyException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
