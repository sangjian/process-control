package cn.ideabuffer.process.core.context;

import cn.ideabuffer.process.core.block.Block;

/**
 * 流程上下文，用于保存流程参数以及获取当前作用域{@link Block}。
 *
 * @author sangjian.sj
 * @date 2020/01/18
 * @see Parameter
 * @see Block
 */
public interface Context extends Parameter {

    /**
     * 获取当前区块
     *
     * @return 当前区块
     */
    Block getBlock();

    /**
     * 克隆流程上下文，新建流程上下文，并复制当前上下文中的参数至新的上下文对象。
     *
     * @return 克隆后的新流程上下文
     */
    Context cloneContext();

    /**
     * 当前key是否可读。
     *
     * @param key 参数key
     * @return 当前key可读，返回true，否则返回false
     */
    boolean readableKey(Key<?> key);

    /**
     * 当前key是否可写。
     *
     * @param key 参数key
     * @return 当前key可写，返回true，否则返回false
     */
    boolean writableKey(Key<?> key);

    /**
     * 获取当前异常对象
     *
     * @return 当前异常对象
     */
    Exception getCurrentException();

    /**
     * 设置当前异常对象
     *
     * @param e 当前异常对象
     */
    void setCurrentException(Exception e);

    /**
     * 获取参数映射器
     * @return 参数映射器
     */
    KeyMapper getMapper();
}
