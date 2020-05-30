package cn.ideabuffer.process.core.status;

/**
 * @author sangjian.sj
 * @date 2020/05/28
 */
public class InnerProcessStatus extends ProcessStatus {
    private static final long serialVersionUID = -9004945559952984969L;

    InnerProcessStatus(boolean proceed) {
        super(proceed);
    }

    @Override
    public void setErrorCode(String code, String message) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ProcessErrorCode getErrorCode() {
        return null;
    }

    @Override
    public void setErrorCode(ProcessErrorCode errorCode) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getCode() {
        return null;
    }

    @Override
    public String getMessage() {
        return null;
    }

}
