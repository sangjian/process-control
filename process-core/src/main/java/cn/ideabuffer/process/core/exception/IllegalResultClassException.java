package cn.ideabuffer.process.core.exception;

/**
 * @author sangjian.sj
 * @date 2020/06/09
 */
public class IllegalResultClassException extends RuntimeException {

    public IllegalResultClassException() {
    }

    public IllegalResultClassException(String message) {
        super(message);
    }

    public IllegalResultClassException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalResultClassException(Throwable cause) {
        super(cause);
    }

    public IllegalResultClassException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
