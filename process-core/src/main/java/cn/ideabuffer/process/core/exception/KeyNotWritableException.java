package cn.ideabuffer.process.core.exception;

/**
 * @author sangjian.sj
 * @date 2020/06/09
 */
public class KeyNotWritableException extends RuntimeException {

    public KeyNotWritableException() {
    }

    public KeyNotWritableException(String message) {
        super(message);
    }

    public KeyNotWritableException(String message, Throwable cause) {
        super(message, cause);
    }

    public KeyNotWritableException(Throwable cause) {
        super(cause);
    }

    public KeyNotWritableException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
