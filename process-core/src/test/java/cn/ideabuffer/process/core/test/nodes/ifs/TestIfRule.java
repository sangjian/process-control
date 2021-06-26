package cn.ideabuffer.process.core.test.nodes.ifs;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.rules.Rule;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class TestIfRule implements Rule {

    @Override
    public boolean match(Context context) {
        Key<Integer> key = Contexts.newKey("k", int.class);
        int k = context.get(key, 0);
        return k < 5;
    }
}
