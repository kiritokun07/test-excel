package com.kirito.excel.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.kirito.excel.base.BusinessException;
import com.kirito.excel.enums.ErrCodeEnum;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * jackson工具
 *
 * @author kirito
 * @desc ...
 * @date 2022-07-13 16:47:47
 */
@Slf4j
public class JacksonUtil {

    private final static ObjectMapper objectMapper = new ObjectMapper();
    private static final DateTimeFormatter NORM_DATETIME_FORMATTER = createFormatter("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter NORM_DATE_FORMATTER = createFormatter("yyyy-MM-dd");
    private static final DateTimeFormatter NORM_TIME_FORMATTER = createFormatter("HH:mm:ss");

    public static DateTimeFormatter createFormatter(String pattern) {
        return DateTimeFormatter.ofPattern(pattern, Locale.getDefault()).withZone(ZoneId.systemDefault());
    }

    static {
        //在反序列化时，忽略目标对象没有的属性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JavaTimeModule javaTimeModule = new JavaTimeModule();

        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(NORM_DATETIME_FORMATTER));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(NORM_DATE_FORMATTER));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(NORM_TIME_FORMATTER));

        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(NORM_DATETIME_FORMATTER));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(NORM_DATE_FORMATTER));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(NORM_TIME_FORMATTER));

        objectMapper.registerModule(javaTimeModule);
    }

    /**
     * 序列化
     *
     * @param object
     * @param <T>
     * @return
     */
    public static <T> String toJSONString(T object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new BusinessException(ErrCodeEnum.FAIL, e.getMessage());
        }
    }

    public static <T1, T2> T2 convert(T1 object, TypeReference<T2> toValueTypeRef) {
        return objectMapper.convertValue(object, toValueTypeRef);
    }

    public static JsonNode parse(String content) {
        try {
            return objectMapper.readTree(content);
        } catch (JsonProcessingException e) {
            throw new BusinessException(ErrCodeEnum.FAIL, e.getMessage());
        }
    }

    /**
     * 反序列化
     *
     * @param content
     * @param valueTypeRef
     * @param <T>
     * @return
     */
    public static <T> T parse(String content, TypeReference<T> valueTypeRef) {
        try {
            return objectMapper.readValue(content, valueTypeRef);
        } catch (JsonProcessingException e) {
            throw new BusinessException(ErrCodeEnum.FAIL, e.getMessage());
        }
    }

    public static <T> T parse(String content, Class<T> clazz) {
        try {
            return objectMapper.readValue(content, clazz);
        } catch (JsonProcessingException e) {
            throw new BusinessException(ErrCodeEnum.FAIL, e.getMessage());
        }
    }

}

