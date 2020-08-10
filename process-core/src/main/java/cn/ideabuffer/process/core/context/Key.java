package cn.ideabuffer.process.core.context;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * @author sangjian.sj
 * @date 2020/03/26
 */
public class Key<V> {

    /**
     * 参数key
     */
    private Object key;

    /**
     * value类型
     */
    private Class<V> valueType;

    /**
     * 描述
     */
    private String description;

    public Key(@NotNull Object key, @NotNull Class<V> valueType) {
        this(key, valueType, null);
    }

    public Key(@NotNull Object key, @NotNull Class<V> valueType, String description) {
        this.key = key;
        this.valueType = valueType;
        this.description = description;
    }

    public Object getKey() {
        return key;
    }

    public Class<V> getValueType() {
        return valueType;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        Key<?> that = (Key<?>)o;
        return Objects.equals(key, that.key) &&
            Objects.equals(valueType, that.valueType);
    }

    @Override
    public int hashCode() {

        return Objects.hash(key, valueType);
    }
}
