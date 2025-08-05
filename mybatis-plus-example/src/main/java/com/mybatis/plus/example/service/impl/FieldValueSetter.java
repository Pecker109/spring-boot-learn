package com.mybatis.plus.example.service.impl;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * @author Pecker
 * @since 2025/8/5 16:46
 */
public class FieldValueSetter {
    // 类型转换器缓存
    private static final Map<Class<?>, Function<Object, ?>> CONVERTER_CACHE = new ConcurrentHashMap<>();
    private static final Map<Field, Function<Object, ?>> FIELD_CONVERTER_CACHE = new ConcurrentHashMap<>();

    static {
        // 初始化常用类型转换器
        initDefaultConverters();
    }

    private static void initDefaultConverters() {
        // 数字类型
        addConverter(Integer.class, FieldValueSetter::toInteger);
        addConverter(int.class, FieldValueSetter::toInteger);
        addConverter(Long.class, FieldValueSetter::toLong);
        addConverter(long.class, FieldValueSetter::toLong);
        addConverter(Double.class, FieldValueSetter::toDouble);
        addConverter(double.class, FieldValueSetter::toDouble);
        addConverter(Float.class, FieldValueSetter::toFloat);
        addConverter(float.class, FieldValueSetter::toFloat);
        addConverter(BigDecimal.class, FieldValueSetter::toBigDecimal);
        addConverter(BigInteger.class, FieldValueSetter::toBigInteger);
        addConverter(Short.class, FieldValueSetter::toShort);
        addConverter(short.class, FieldValueSetter::toShort);
        addConverter(Byte.class, FieldValueSetter::toByte);
        addConverter(byte.class, FieldValueSetter::toByte);

        // 布尔类型
        addConverter(Boolean.class, FieldValueSetter::toBoolean);
        addConverter(boolean.class, FieldValueSetter::toBoolean);

        // 字符串类型
        addConverter(String.class, FieldValueSetter::toString);
        addConverter(CharSequence.class, FieldValueSetter::toString);
        addConverter(char.class, FieldValueSetter::toChar);
        addConverter(Character.class, FieldValueSetter::toChar);

        // 日期时间类型
        addConverter(Date.class, FieldValueSetter::toSqlDate);
        addConverter(java.util.Date.class, FieldValueSetter::toUtilDate);
        addConverter(Timestamp.class, FieldValueSetter::toTimestamp);
        addConverter(Time.class, FieldValueSetter::toSqlTime);
        addConverter(LocalDate.class, FieldValueSetter::toLocalDate);
        addConverter(LocalDateTime.class, FieldValueSetter::toLocalDateTime);
        addConverter(LocalTime.class, FieldValueSetter::toLocalTime);
        addConverter(Instant.class, FieldValueSetter::toInstant);
        addConverter(ZonedDateTime.class, FieldValueSetter::toZonedDateTime);

        // 其他类型
        addConverter(byte[].class, FieldValueSetter::toByteArray);
    }

    private static <T> void addConverter(Class<T> targetType, Function<Object, T> converter) {
        CONVERTER_CACHE.put(targetType, converter);
    }

    /**
     * 高性能设置字段值
     *
     * @param obj 目标对象
     * @param field 字段
     * @param value 原始值
     */
    public static void setFieldValue(Object obj, Field field, Object value) throws IllegalAccessException {

        if (value == null) {
            // 处理null值
            handleNullValue(obj, field);
            return;
        }

        // 获取字段类型转换器
        Function<Object, ?> converter = FIELD_CONVERTER_CACHE.computeIfAbsent(field, f -> {
            Class<?> fieldType = f.getType();

            // 1. 检查类型是否完全匹配
            if (fieldType.isInstance(value)) {
                return v -> v;
            }

            // 2. 检查是否有注册的转换器
            Function<Object, ?> typeConverter = CONVERTER_CACHE.get(fieldType);
            if (typeConverter != null) {
                return typeConverter;
            }

            // 3. 检查枚举类型
            if (fieldType.isEnum()) {
                return v -> convertToEnum(v, (Class<? extends Enum>) fieldType);
            }

            // 4. 默认转换器（尝试直接赋值）
            return v -> {
                try {
                    return fieldType.cast(v);
                } catch (ClassCastException e) {
                    throw new TypeConversionException("Cannot convert value " + v +
                            " to type " + fieldType.getName(), e);
                }
            };
        });

        // 转换并设置值
        Object convertedValue = converter.apply(value);
        field.set(obj, convertedValue);
    }

    /**
     * 处理null值
     */
    private static void handleNullValue(Object obj, Field field)
            throws IllegalAccessException {

        Class<?> fieldType = field.getType();

        if (fieldType.isPrimitive()) {
            // 基本类型处理默认值
            if (fieldType == int.class) {
                field.setInt(obj, 0);
            } else if (fieldType == long.class) {
                field.setLong(obj, 0L);
            } else if (fieldType == double.class) {
                field.setDouble(obj, 0.0);
            } else if (fieldType == float.class) {
                field.setFloat(obj, 0.0f);
            } else if (fieldType == boolean.class) {
                field.setBoolean(obj, false);
            } else if (fieldType == short.class) {
                field.setShort(obj, (short) 0);
            } else if (fieldType == byte.class) {
                field.setByte(obj, (byte) 0);
            } else if (fieldType == char.class) {
                field.setChar(obj, '\u0000');
            }
        } else {
            // 对象类型设置为null
            field.set(obj, null);
        }
    }

    // ========== 类型转换方法 ==========

    private static Integer toInteger(Object value) {
        if (value instanceof Number) {
            return ((Number) value).intValue();
        } else if (value instanceof CharSequence) {
            return Integer.parseInt(value.toString());
        } else if (value instanceof Boolean) {
            return (Boolean) value ? 1 : 0;
        }
        throw new TypeConversionException("Cannot convert to Integer: " + value);
    }

    private static Long toLong(Object value) {
        if (value instanceof Number) {
            return ((Number) value).longValue();
        } else if (value instanceof CharSequence) {
            return Long.parseLong(value.toString());
        } else if (value instanceof Boolean) {
            return (Boolean) value ? 1L : 0L;
        } else if (value instanceof Date) {
            return ((Date) value).getTime();
        } else if (value instanceof java.util.Date) {
            return ((java.util.Date) value).getTime();
        }
        throw new TypeConversionException("Cannot convert to Long: " + value);
    }

    private static Double toDouble(Object value) {
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        } else if (value instanceof CharSequence) {
            return Double.parseDouble(value.toString());
        } else if (value instanceof Boolean) {
            return (Boolean) value ? 1.0 : 0.0;
        }
        throw new TypeConversionException("Cannot convert to Double: " + value);
    }

    private static Float toFloat(Object value) {
        if (value instanceof Number) {
            return ((Number) value).floatValue();
        } else if (value instanceof CharSequence) {
            return Float.parseFloat(value.toString());
        } else if (value instanceof Boolean) {
            return (Boolean) value ? 1.0f : 0.0f;
        }
        throw new TypeConversionException("Cannot convert to Float: " + value);
    }

    private static BigDecimal toBigDecimal(Object value) {
        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        } else if (value instanceof Number) {
            return BigDecimal.valueOf(((Number) value).doubleValue());
        } else if (value instanceof CharSequence) {
            return new BigDecimal(value.toString());
        }
        throw new TypeConversionException("Cannot convert to BigDecimal: " + value);
    }

    private static BigInteger toBigInteger(Object value) {
        if (value instanceof BigInteger) {
            return (BigInteger) value;
        } else if (value instanceof Number) {
            return BigInteger.valueOf(((Number) value).longValue());
        } else if (value instanceof CharSequence) {
            return new BigInteger(value.toString());
        }
        throw new TypeConversionException("Cannot convert to BigInteger: " + value);
    }

    private static Boolean toBoolean(Object value) {
        if (value instanceof Boolean) {
            return (Boolean) value;
        } else if (value instanceof Number) {
            return ((Number) value).intValue() != 0;
        } else if (value instanceof CharSequence) {
            String strVal = value.toString().toLowerCase();
            return "true".equals(strVal) || "1".equals(strVal) || "yes".equals(strVal);
        }
        throw new TypeConversionException("Cannot convert to Boolean: " + value);
    }

    private static String toString(Object value) {
        return value.toString();
    }

    private static Character toChar(Object value) {
        if (value instanceof Character) {
            return (Character) value;
        } else if (value instanceof Number) {
            return (char) ((Number) value).intValue();
        } else if (value instanceof CharSequence && value.toString().length() > 0) {
            return value.toString().charAt(0);
        }
        throw new TypeConversionException("Cannot convert to Character: " + value);
    }

    private static Short toShort(Object value) {
        if (value instanceof Number) {
            return ((Number) value).shortValue();
        } else if (value instanceof CharSequence) {
            return Short.parseShort(value.toString());
        }
        throw new TypeConversionException("Cannot convert to Short: " + value);
    }

    private static Byte toByte(Object value) {
        if (value instanceof Number) {
            return ((Number) value).byteValue();
        } else if (value instanceof CharSequence) {
            return Byte.parseByte(value.toString());
        }
        throw new TypeConversionException("Cannot convert to Byte: " + value);
    }

    private static java.sql.Date toSqlDate(Object value) {
        if (value instanceof java.sql.Date) {
            return (java.sql.Date) value;
        } else if (value instanceof java.util.Date) {
            return new java.sql.Date(((java.util.Date) value).getTime());
        } else if (value instanceof Number) {
            return new java.sql.Date(((Number) value).longValue());
        } else if (value instanceof CharSequence) {
            return java.sql.Date.valueOf(value.toString());
        } else if (value instanceof LocalDate) {
            return java.sql.Date.valueOf((LocalDate) value);
        }
        throw new TypeConversionException("Cannot convert to java.sql.Date: " + value);
    }

    private static java.util.Date toUtilDate(Object value) {
        if (value instanceof java.util.Date) {
            return (java.util.Date) value;
        } else if (value instanceof java.sql.Date) {
            return new java.util.Date(((java.sql.Date) value).getTime());
        } else if (value instanceof Timestamp) {
            return new java.util.Date(((Timestamp) value).getTime());
        } else if (value instanceof Number) {
            return new java.util.Date(((Number) value).longValue());
        } else if (value instanceof CharSequence) {
            return java.util.Date.from(Instant.parse(value.toString()));
        } else if (value instanceof LocalDateTime) {
            return java.util.Date.from(((LocalDateTime) value).atZone(ZoneId.systemDefault()).toInstant());
        } else if (value instanceof LocalDate) {
            return java.util.Date.from(((LocalDate) value).atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        throw new TypeConversionException("Cannot convert to java.util.Date: " + value);
    }

    private static Timestamp toTimestamp(Object value) {
        if (value instanceof Timestamp) {
            return (Timestamp) value;
        } else if (value instanceof java.util.Date) {
            return new Timestamp(((java.util.Date) value).getTime());
        } else if (value instanceof Number) {
            return new Timestamp(((Number) value).longValue());
        } else if (value instanceof CharSequence) {
            return Timestamp.valueOf(value.toString());
        } else if (value instanceof LocalDateTime) {
            return Timestamp.valueOf((LocalDateTime) value);
        }
        throw new TypeConversionException("Cannot convert to Timestamp: " + value);
    }

    private static java.sql.Time toSqlTime(Object value) {
        if (value instanceof Time) {
            return (Time) value;
        } else if (value instanceof java.util.Date) {
            return new Time(((java.util.Date) value).getTime());
        } else if (value instanceof Number) {
            return new Time(((Number) value).longValue());
        } else if (value instanceof CharSequence) {
            return Time.valueOf(value.toString());
        } else if (value instanceof LocalTime) {
            return Time.valueOf((LocalTime) value);
        }
        throw new TypeConversionException("Cannot convert to java.sql.Time: " + value);
    }

    private static LocalDate toLocalDate(Object value) {
        if (value instanceof LocalDate) {
            return (LocalDate) value;
        } else if (value instanceof java.sql.Date) {
            return ((java.sql.Date) value).toLocalDate();
        } else if (value instanceof java.util.Date) {
            return ((java.util.Date) value).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } else if (value instanceof CharSequence) {
            return LocalDate.parse(value.toString());
        } else if (value instanceof Number) {
            return Instant.ofEpochMilli(((Number) value).longValue())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        }
        throw new TypeConversionException("Cannot convert to LocalDate: " + value);
    }

    private static LocalDateTime toLocalDateTime(Object value) {
        if (value instanceof LocalDateTime) {
            return (LocalDateTime) value;
        } else if (value instanceof Timestamp) {
            return ((Timestamp) value).toLocalDateTime();
        } else if (value instanceof java.util.Date) {
            return ((java.util.Date) value).toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
        } else if (value instanceof CharSequence) {
            return LocalDateTime.parse(value.toString());
        } else if (value instanceof Number) {
            return Instant.ofEpochMilli(((Number) value).longValue())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
        }
        throw new TypeConversionException("Cannot convert to LocalDateTime: " + value);
    }

    private static LocalTime toLocalTime(Object value) {
        if (value instanceof LocalTime) {
            return (LocalTime) value;
        } else if (value instanceof Time) {
            return ((Time) value).toLocalTime();
        } else if (value instanceof CharSequence) {
            return LocalTime.parse(value.toString());
        } else if (value instanceof Number) {
            return Instant.ofEpochMilli(((Number) value).longValue())
                    .atZone(ZoneId.systemDefault())
                    .toLocalTime();
        }
        throw new TypeConversionException("Cannot convert to LocalTime: " + value);
    }

    private static Instant toInstant(Object value) {
        if (value instanceof Instant) {
            return (Instant) value;
        } else if (value instanceof java.util.Date) {
            return ((java.util.Date) value).toInstant();
        } else if (value instanceof Number) {
            return Instant.ofEpochMilli(((Number) value).longValue());
        } else if (value instanceof CharSequence) {
            return Instant.parse(value.toString());
        }
        throw new TypeConversionException("Cannot convert to Instant: " + value);
    }

    private static ZonedDateTime toZonedDateTime(Object value) {
        if (value instanceof ZonedDateTime) {
            return (ZonedDateTime) value;
        } else if (value instanceof java.util.Date) {
            return ZonedDateTime.ofInstant(((java.util.Date) value).toInstant(), ZoneId.systemDefault());
        } else if (value instanceof CharSequence) {
            return ZonedDateTime.parse(value.toString());
        } else if (value instanceof Number) {
            return ZonedDateTime.ofInstant(
                    Instant.ofEpochMilli(((Number) value).longValue()),
                    ZoneId.systemDefault()
            );
        }
        throw new TypeConversionException("Cannot convert to ZonedDateTime: " + value);
    }

    private static byte[] toByteArray(Object value) {
        if (value instanceof byte[]) {
            return (byte[]) value;
        } else if (value instanceof Byte[]) {
            Byte[] byteArray = (Byte[]) value;
            byte[] result = new byte[byteArray.length];
            for (int i = 0; i < byteArray.length; i++) {
                result[i] = byteArray[i];
            }
            return result;
        } else if (value instanceof CharSequence) {
            return value.toString().getBytes();
        }
        throw new TypeConversionException("Cannot convert to byte[]: " + value);
    }

    private static <E extends Enum<E>> E convertToEnum(Object value, Class<E> enumType) {
        if (enumType.isInstance(value)) {
            return enumType.cast(value);
        }

        if (value instanceof Number) {
            int ordinal = ((Number) value).intValue();
            E[] constants = enumType.getEnumConstants();
            if (ordinal >= 0 && ordinal < constants.length) {
                return constants[ordinal];
            }
        } else if (value instanceof CharSequence) {
            String name = value.toString();
            try {
                return Enum.valueOf(enumType, name);
            } catch (IllegalArgumentException e) {
                // 尝试忽略大小写匹配
                for (E constant : enumType.getEnumConstants()) {
                    if (constant.name().equalsIgnoreCase(name)) {
                        return constant;
                    }
                }
            }
        }

        throw new TypeConversionException("Cannot convert " + value + " to enum " + enumType.getName());
    }

    /**
     * 类型转换异常
     */
    public static class TypeConversionException extends RuntimeException {
        public TypeConversionException(String message) {
            super(message);
        }

        public TypeConversionException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
