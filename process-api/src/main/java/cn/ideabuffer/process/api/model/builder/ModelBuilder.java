package cn.ideabuffer.process.api.model.builder;

import cn.ideabuffer.process.api.model.Model;

/**
 * @author sangjian.sj
 * @date 2020/06/23
 */
public class ModelBuilder<R> {

    public Model build(R resource) {
        return new Model<>(resource);
    }

}
