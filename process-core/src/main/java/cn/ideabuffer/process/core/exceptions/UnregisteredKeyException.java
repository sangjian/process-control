package cn.ideabuffer.process.core.exceptions;

/**
 * @author sangjian.sj
 * @date 2020/06/09
 */
public class UnregisteredKeyException extends RuntimeException {

    private static final long serialVersionUID = 5344792732236522159L;

    public UnregisteredKeyException() {
    }

    public UnregisteredKeyException(String message) {
        super(message);
    }

    public UnregisteredKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnregisteredKeyException(Throwable cause) {
        super(cause);
    }

    public UnregisteredKeyException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
