package cn.ideabuffer.process.core.block;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Parameter;
import cn.ideabuffer.process.core.context.ParameterImpl;

import java.util.Objects;

/**
 * 用于表示一组节点所在的范围，通过Context获取，同一block内，数据可共享，与当前context数据隔离
 *
 * @author sangjian.sj
 * @date 2020/02/22
 * @see Context#getBlock()
 */
public interface Block extends Parameter {

    boolean allowBreak();

    boolean allowContinue();

    void doBreak();

    void doContinue();

    boolean hasBroken();

    boolean hasContinued();

    Block getParent();

}
