package cn.ideabuffer.process.api.model;

import cn.ideabuffer.process.annotation.model.ResourceModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * @author sangjian.sj
 * @date 2020/06/20
 */
public class Model<R> implements Serializable {

    private static final long serialVersionUID = -1530203417363796983L;

    private String id;

    private String name;

    private String className;

    private String description;

    @JsonIgnore
    protected R resource;

    public Model(@NotNull R resource) {
        this.resource = resource;
        init();
    }

    protected void init() {
        this.setId(resource.getClass().getName());
        this.setName(resource.getClass().getName());
        this.setClassName(resource.getClass().getName());
        ResourceModel pm = resource.getClass().getAnnotation(ResourceModel.class);
        if (pm == null) {
            return;
        }
        if (!"".equals(pm.id())) {
            this.id = pm.id();
        }
        if (!"".equals(pm.name())) {
            this.name = pm.name();
        }
        if(!"".equals(pm.description())) {
            this.description = pm.description();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public R getResource() {
        return resource;
    }

}
