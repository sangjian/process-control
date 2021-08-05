package cn.ideabuffer.process.core.exceptions;

/**
 * @author sangjian.sj
 * @date 2020/06/09
 */
public class IllegalResultClassException extends RuntimeException {

    private static final long serialVersionUID = 5888747105322976886L;

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
