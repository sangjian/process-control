package cn.ideabuffer.process.core.exception;

/**
 * @author sangjian.sj
 * @date 2020/06/09
 */
public class UnwritableKeyException extends RuntimeException {

    private static final long serialVersionUID = 8923976626467306690L;

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
