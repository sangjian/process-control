package cn.ideabuffer.process.status;

import java.io.Serializable;

/**
 * @author sangjian.sj
 * @date 2020/03/24
 */
public interface ProcessErrorCodeProvider extends Serializable {

    /**
     * 获取ResultCode
     *
     * @return
     */
    ProcessErrorCode getErrorCode();

}
