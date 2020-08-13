package cn.ideabuffer.process.core.exception;

/**
 * @author sangjian.sj
 * @date 2020/06/09
 */
public class KeyNotRegisteredException extends RuntimeException {

    public KeyNotRegisteredException() {
    }

    public KeyNotRegisteredException(String message) {
        super(message);
    }

    public KeyNotRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }

    public KeyNotRegisteredException(Throwable cause) {
        super(cause);
    }

    public KeyNotRegisteredException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
