package cn.ideabuffer.process.core.test;

import cn.ideabuffer.process.core.block.Block;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.context.Key;
import org.junit.Test;

/**
 * @author sangjian.sj
 * @date 2020/04/01
 */
public class ContextTest {

    @Test
    public void contextViewBlockTest() {
        Context context = Contexts.newContext();
        Key<Integer> key = Contexts.newKey("k", int.class);
        context.put(key, 1);
        Block block = context.getBlock();
        block.put(key, 2);
        System.out.println(block.get(key));
        System.out.println(context.get(key));
    }

    @Test
    public void testBlockInherit() {
        Context context = Contexts.newContext();
        Key<Integer> key = Contexts.newKey("k", int.class);
        context.put(key, 1);
        Block parent = context.getBlock();
        Block block = new Block(parent);
        parent.put(key, 2);
        System.out.println(parent.get(key));
        System.out.println(block.get(key));
        System.out.println(context.get(key));

        block.put(key, 3);
        System.out.println(parent.get(key));
        System.out.println(block.get(key));
        System.out.println(context.get(key));
    }

}
