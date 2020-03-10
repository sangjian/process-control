package cn.ideabuffer.process.nodes.transmitter;

import cn.ideabuffer.process.Context;

/**
 * @author sangjian.sj
 * @date 2020/03/10
 */
public class TestTransmittableNode extends AbstractTransmittableNode<String> {
    @Override
    protected String doInvoke(Context context) throws Exception {
        return "hello";
    }
}
