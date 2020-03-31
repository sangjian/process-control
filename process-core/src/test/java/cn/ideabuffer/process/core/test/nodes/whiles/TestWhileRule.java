package cn.ideabuffer.process.core.test.nodes.whiles;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.rule.Rule;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class TestWhileRule implements Rule {

    @Override
    public boolean match(Context context) {
        Key<Integer> key = Contexts.newKey("k", int.class);
        int k = context.getBlock().get(key, 0);
        return k < 10;
    }
}
