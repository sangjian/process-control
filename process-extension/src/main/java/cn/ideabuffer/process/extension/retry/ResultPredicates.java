package cn.ideabuffer.process.extension.retry;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author sangjian.sj
 * @date 2020/04/29
 */
public final class ResultPredicates {

    public static final Double DOUBLE_ZERO = 0.0d;

    public static final Float FLOAT_ZERO = 0.0f;

    public static final Predicate<String> IS_EMPTY_STRING_PREDICATE = new IsEmptyString();

    private ResultPredicates() {}

    public static <T> Predicate<T> isNull() {
        return ObjectPredicate.IS_NULL.withNarrowedType();
    }

    public static <T> Predicate<T> isNotNull() {
        return Predicates.not(isNull());
    }

    public static Predicate<Boolean> isTrue() {
        return BooleanPredicate.IS_TRUE;
    }

    public static Predicate<Boolean> isFalse() {
        return BooleanPredicate.IS_FALSE;
    }

    public static Predicate<Number> isZero() {
        return NumberPredicate.IS_ZEOR;
    }

    public static Predicate<Number> isNegative() {
        return NumberPredicate.IS_NEGATIVE;
    }

    public static Predicate<Number> isPositive() {
        return NumberPredicate.IS_POSITIVE;
    }

    public static Predicate<String> isEmptyString() {
        return IS_EMPTY_STRING_PREDICATE;
    }

    public static Predicate<String> isNotEmptyString() {
        return Predicates.not(IS_EMPTY_STRING_PREDICATE);
    }

    public static <T> Predicate<Collection<? extends T>> isEmptyCollection() {
        return new IsEmptyCollection<>();
    }


    public static <T> Predicate<Collection<? extends T>> isNotEmptyCollection() {
        return Predicates.not(isEmptyCollection());
    }

    public static <K, V> Predicate<Map<K, V>> isEmptyMap() {
        return new IsEmptyMap<>();
    }

    public static <K, V> Predicate<Map<K, V>> isNotEmptyMap() {
        return Predicates.not(isEmptyMap());
    }

    enum ObjectPredicate implements Predicate<Object> {

        /**
         * 值为null
         */
        IS_NULL {
            @Override
            public boolean apply(@Nullable Object o) {
                return o == null;
            }

            @Override
            public String toString() {
                return "Predicates.isNull()";
            }
        };

        @SuppressWarnings("unchecked") // safe contravariant cast
        <T> Predicate<T> withNarrowedType() {
            return (Predicate<T>) this;
        }
    }

    enum BooleanPredicate implements Predicate<Boolean> {

        /**
         * 是否为true
         */
        IS_TRUE {

            @Override
            public boolean apply(@Nullable Boolean input) {
                return Boolean.TRUE.equals(input);
            }


            @Override
            public String toString() {
                return "ResultPredicates.isTrue()";
            }
        },
        /**
         * 是否为false
         */
        IS_FALSE {

            @Override
            public boolean apply(@Nullable Boolean input) {
                return Boolean.FALSE.equals(input);
            }


            @Override
            public String toString() {
                return "ResultPredicates.isFalse()";
            }
        }
    }

    enum NumberPredicate implements Predicate<Number> {
        /**
         * 值为0
         */
        IS_ZEOR {
            @Override
            public boolean apply(@Nullable Number input) {
                checkNotNull(input);
                if(input instanceof Float) {
                    return Float.compare((Float)input, FLOAT_ZERO) == 0;
                }
                if(input instanceof Double) {
                    return Double.compare((Double)input, DOUBLE_ZERO) == 0;
                }
                return input.longValue() == 0;
            }


            @Override
            public String toString() {
                return "ResultPredicates.isZero()";
            }
        },
        /**
         * 值为负数
         */
        IS_NEGATIVE {
            @Override
            public boolean apply(@Nullable Number input) {
                checkNotNull(input);
                if(input instanceof Float) {
                    return Float.compare((Float)input, FLOAT_ZERO) < 0;
                }
                if(input instanceof Double) {
                    return Double.compare((Double)input, DOUBLE_ZERO) < 0;
                }
                return input.longValue() < 0;
            }


            @Override
            public String toString() {
                return "ResultPredicates.isNegative()";
            }
        },
        /**
         * 值为正数
         */
        IS_POSITIVE {
            @Override
            public boolean apply(@Nullable Number input) {
                checkNotNull(input);
                if(input instanceof Float) {
                    return Float.compare((Float)input, FLOAT_ZERO) > 0;
                }
                if(input instanceof Double) {
                    return Double.compare((Double)input, DOUBLE_ZERO) > 0;
                }
                return input.longValue() > 0;
            }


            @Override
            public String toString() {
                return "ResultPredicates.isPositive()";
            }
        }
    }

    private static class IsEmptyString implements Predicate<String>, Serializable {

        @Override
        public boolean apply(@Nullable String value) {
            return value == null || "".equals(value);
        }

        @Override
        public String toString() {
            return "Predicates.IsEmptyString()";
        }

    }

    private static class IsEmptyCollection<T> implements Predicate<Collection<? extends T>>, Serializable {

        @Override
        public boolean apply(@Nullable Collection<? extends T> input) {
            return input == null || input.size() == 0;
        }

        @Override
        public String toString() {
            return "Predicates.IsEmptyCollection()";
        }

    }

    private static class IsEmptyMap<K, V> implements Predicate<Map<K, V>>, Serializable {

        @Override
        public boolean apply(@Nullable Map<K, V> input) {
            return input == null || input.size() == 0;
        }

        @Override
        public String toString() {
            return "Predicates.IsEmptyCollection()";
        }

    }

    public static void main(String[] args) {
        ObjectPredicate op = ObjectPredicate.IS_NULL;
        System.out.println(op.apply(null));
    }

}
