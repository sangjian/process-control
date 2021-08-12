package cn.ideabuffer.process.core;

import java.util.List;

/**
 * @author sangjian
 * @date 2021/8/7
 */
public interface ComplexNodes<N extends Node> {

    List<N> getNodes();

}
