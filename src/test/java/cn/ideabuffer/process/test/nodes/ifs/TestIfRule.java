package cn.ideabuffer.process.test.nodes.ifs;

import cn.ideabuffer.process.context.Context;
import cn.ideabuffer.process.context.Contexts;
import cn.ideabuffer.process.context.Key;
import cn.ideabuffer.process.rule.Rule;

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
