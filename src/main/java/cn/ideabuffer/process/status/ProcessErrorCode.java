package cn.ideabuffer.process.status;

import java.io.Serializable;

/**
 * @author sangjian.sj
 * @date 2020/03/24
 */
public class ProcessErrorCode implements Serializable {
    private static final long serialVersionUID = -4794736288978072418L;

    public static final ProcessErrorCode SYSTEM_ERROR = new ProcessErrorCode("system_error", "系统错误");
    public static final ProcessErrorCode PARAMS_ERROR = new ProcessErrorCode("params_error", "参数错误");

    private String code;
    private String message;

    public ProcessErrorCode() {
    }

    public ProcessErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public void setCode(int code) {
        this.code = String.valueOf(code);
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ProcessErrorCode{" +
            "code='" + code + '\'' +
            ", message='" + message + '\'' +
            '}';
    }
}
