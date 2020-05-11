package cn.ideabuffer.process.core.processors;

import cn.ideabuffer.process.core.Processor;

/**
 * @author sangjian.sj
 * @date 2020/05/11
 */
public interface CompoundProcessor<V> extends Processor<V> {

    Processor<V> getProcessor();

}
