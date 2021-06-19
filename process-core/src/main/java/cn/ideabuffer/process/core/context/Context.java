package cn.ideabuffer.process.core.context;

import cn.ideabuffer.process.core.ProcessDefinition;
import cn.ideabuffer.process.core.block.Block;

/**
 * 流程上下文
 *
 * @author sangjian.sj
 * @date 2020/01/18
 */
public interface Context extends Parameter {

    /**
     * 获取当前区块
     *
     * @return 当前区块
     */
    Block getBlock();

    Context cloneContext();

    /**
     * 获取结果key
     *
     * @param <V>
     * @return
     */
    <V> Key<V> getResultKey();

    void setResultKey(ProcessDefinition<?> definition);

    /**
     * 当前key是否可读
     *
     * @param key
     * @return
     */
    boolean readableKey(Key<?> key);

    /**
     * 当前key是否可写
     *
     * @param key
     * @return
     */
    boolean writableKey(Key<?> key);

    /**
     * 获取当前异常对象
     *
     * @return
     */
    Exception getCurrentException();

    /**
     * 设置当前异常对象
     *
     * @param e
     */
    void setCurrentException(Exception e);
}
