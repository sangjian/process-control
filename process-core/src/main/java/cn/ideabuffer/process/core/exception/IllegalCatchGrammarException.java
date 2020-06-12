package cn.ideabuffer.process.core.exception;

/**
 * @author sangjian.sj
 * @date 2020/06/09
 */
public class IllegalCatchGrammarException extends RuntimeException {

    public IllegalCatchGrammarException() {
    }

    public IllegalCatchGrammarException(String message) {
        super(message);
    }

    public IllegalCatchGrammarException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalCatchGrammarException(Throwable cause) {
        super(cause);
    }

    public IllegalCatchGrammarException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
