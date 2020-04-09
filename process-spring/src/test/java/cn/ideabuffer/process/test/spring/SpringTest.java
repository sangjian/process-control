package cn.ideabuffer.process.test.spring;

import cn.ideabuffer.process.core.ProcessDefinition;
import cn.ideabuffer.process.core.ProcessInstance;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.test.spring.key.Keys;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author sangjian.sj
 * @date 2020/04/08
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class SpringTest {

    @Resource
    private ProcessDefinition<String> testDefinition;

    @Resource
    private ProcessInstance<Integer> testInstance;

    @Test
    public void test() throws Exception {
        ProcessInstance<String> instance = testDefinition.newInstance();
        Context context = Contexts.newContext();
        context.put(Keys.testKey1, "haha");
        System.out.println(instance.execute(context));
        System.out.println(instance.getResult());
    }
}
