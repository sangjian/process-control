package cn.ideabuffer.process.core.context;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.Objects;

/**
 * 参数的键，以此来进行参数的映射。
 *
 * @param <V> 值的类型
 * @author sangjian.sj
 * @date 2020/03/26
 */
public class Key<V> implements Serializable {

    private static final long serialVersionUID = 6760427218917294279L;

    /**
     * 参数key
     */
    private Serializable key;

    /**
     * value类型
     */
    private Class<? super V> valueType;

    /**
     * 描述
     */
    @Nullable
    private String description;

    public Key(@NotNull Serializable key, @NotNull Class<? super V> valueType) {
        this(key, valueType, null);
    }

    public Key(@NotNull Serializable key, @NotNull Class<? super V> valueType, @Nullable String description) {
        this.key = key;
        this.valueType = valueType;
        this.description = description;
    }

    public Serializable getKey() {
        return key;
    }

    public Class<? super V> getValueType() {
        return valueType;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        Key<?> key1 = (Key<?>)o;
        return Objects.equals(key, key1.key) &&
            Objects.equals(valueType, key1.valueType) &&
            Objects.equals(description, key1.description);
    }

    @Override
    public int hashCode() {

        return Objects.hash(key, valueType, description);
    }

    @Override
    public String toString() {
        return "Key{" +
            "key=" + key +
            ", valueType=" + valueType +
            ", description='" + description + '\'' +
            '}';
    }
}
