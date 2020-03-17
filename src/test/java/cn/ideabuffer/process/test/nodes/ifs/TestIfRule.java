package cn.ideabuffer.process.test.nodes.ifs;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.rule.Rule;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class TestIfRule implements Rule {

    @Override
    public boolean match(Context context) {
        int k = context.get("k", 5);
        return k < 5;
    }
}
