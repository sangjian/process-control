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

    void setResultKey(ProcessDefinition<?> definition);

    <V> Key<V> getResultKey();

    boolean keyAvailable(Key<?> key);

    Exception getCurrentException();

    void setCurrentException(Exception e);
}
