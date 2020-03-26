package cn.ideabuffer.process.context;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * @author sangjian.sj
 * @date 2020/03/26
 */
public class ContextKey<V> {

    private Object key;

    private Class<V> valueType;

    public ContextKey(@NotNull Object key, Class<V> valueType) {
        this.key = key;
        this.valueType = valueType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        ContextKey<?> that = (ContextKey<?>)o;
        return Objects.equals(key, that.key) &&
            Objects.equals(valueType, that.valueType);
    }

    @Override
    public int hashCode() {

        return Objects.hash(key, valueType);
    }
}
