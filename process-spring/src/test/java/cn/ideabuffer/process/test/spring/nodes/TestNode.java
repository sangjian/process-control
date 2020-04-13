package cn.ideabuffer.process.test.spring.nodes;

import org.springframework.beans.factory.DisposableBean;

/**
 * @author sangjian.sj
 * @date 2020/04/09
 */
public class TestNode implements DisposableBean {
    @Override
    public void destroy() throws Exception {
        System.out.println("in testNode destroy");
    }
}
