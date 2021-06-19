package cn.ideabuffer.process.core.exception;

/**
 * @author sangjian.sj
 * @date 2020/04/06
 */
public class ProcessException extends RuntimeException {
    private static final long serialVersionUID = -6920149606131940717L;

    public ProcessException() {
    }

    public ProcessException(String message) {
        super(message);
    }

    public ProcessException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProcessException(Throwable cause) {
        super(cause);
    }

    public ProcessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
