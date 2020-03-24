package cn.ideabuffer.process;

import cn.ideabuffer.process.nodes.ExecutableNode;

/**
 * 流程实例
 *
 * @author sangjian.sj
 * @date 2020/01/18
 */
public interface ProcessInstance<R> extends ExecutableNode {

    ProcessDefine<R> getProcessDefine();

    R getResult();

}
