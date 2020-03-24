package cn.ideabuffer.process.nodes;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.Node;

/**
 * @author sangjian.sj
 * @date 2020/03/24
 */
public interface BaseNode<R> extends Node {

    R invoke(Context context);

}
