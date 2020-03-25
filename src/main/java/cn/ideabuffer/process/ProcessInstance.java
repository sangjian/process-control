package cn.ideabuffer.process;

import cn.ideabuffer.process.nodes.ExecutableNode;

/**
 * 流程实例接口，一个流程实例表示一次具体的业务处理流程。
 *
 * @author sangjian.sj
 * @date 2020/01/18
 */
public interface ProcessInstance<R> extends ExecutableNode {

    /**
     * 获取流程定义
     *
     * @return 流程定义
     */
    ProcessDefinition<R> getProcessDefinition();

    /**
     * 获取返回结果
     *
     * @return 结果
     */
    R getResult();

}
