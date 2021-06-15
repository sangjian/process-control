package cn.ideabuffer.process.core.test;

import cn.ideabuffer.process.core.block.Block;
import cn.ideabuffer.process.core.block.InnerBlock;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.context.Key;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author sangjian.sj
 * @date 2020/04/01
 */
public class ContextTest {

    /**
     * 测试根Block
     */
    @Test
    public void contextViewBlockTest() {
        Context context = Contexts.newContext();
        Key<Integer> key = Contexts.newKey("k", int.class);
        context.put(key, 1);
        // ViewBlock相当于Context的一个视图，其操作就是对Context的操作
        Block block = context.getBlock();
        assertEquals(1, (int)block.get(key));
        block.put(key, 2);
        assertEquals(2, (int)context.get(key));
    }

    /**
     * 测试继承
     */
    @Test
    public void testBlockInherit() {
        Context context = Contexts.newContext();
        Key<Integer> key = Contexts.newKey("k", int.class);
        context.put(key, 1);
        Block parent = context.getBlock();
        Block block = new InnerBlock(parent);

        parent.put(key, 2);
        assertEquals(2, (int)parent.get(key));
        // block值不会变
        assertEquals(1, (int)block.get(key));
        // parent操作与context操作等价
        assertEquals(2, (int)context.get(key));

        // block设置值
        block.put(key, 3);
        // parent不会变
        assertEquals(2, (int)parent.get(key));
        // block对应的key的值为设置后的值
        assertEquals(3, (int)block.get(key));
        // context不会变
        assertEquals(2, (int)context.get(key));
    }

}
