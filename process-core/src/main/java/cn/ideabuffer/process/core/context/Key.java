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

    public Key(@NotNull Object key, @NotNull Class<V> valueType) {
        this.key = key;
        this.valueType = valueType;
    }

    public Object getKey() {
        return key;
    }

    public Class<V> getValueType() {
        return valueType;
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
