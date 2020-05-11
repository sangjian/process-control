package cn.ideabuffer.process.core;

import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.processors.impl.ProcessInstanceProcessorImpl;
import cn.ideabuffer.process.core.status.ProcessStatus;

/**
 * 流程实例接口，一个流程实例表示一次具体的业务处理流程。
 *
 * @author sangjian.sj
 * @date 2020/01/18
 */
public interface ProcessInstance<R> extends ExecutableNode<ProcessStatus, ProcessInstanceProcessorImpl<R>> {

    R getResult();

}
