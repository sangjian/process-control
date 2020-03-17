package cn.ideabuffer.process.test.nodes.whiles;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.rule.Rule;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class TestWhileRule implements Rule {

    @Override
    public boolean match(Context context) {
        int k = context.getBlock().get("k", 0);
        return k < 10;
    }
}
