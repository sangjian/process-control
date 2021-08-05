package cn.ideabuffer.process.core.processors.wrapper.exception;

/**
 * @author sangjian.sj
 * @date 2021/06/19
 */
public class WrapperHandlerProcessException extends RuntimeException {
    private static final long serialVersionUID = 3230579243387764430L;

    public WrapperHandlerProcessException() {
    }

    public WrapperHandlerProcessException(String message) {
        super(message);
    }

    public WrapperHandlerProcessException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrapperHandlerProcessException(Throwable cause) {
        super(cause);
    }

    public WrapperHandlerProcessException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
