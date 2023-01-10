package pers.ken.rt.common.utils;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * <name> Jackson </name>
 * <desc> Jackson </desc>
 * Creation Time: 2021/9/19 17:37.
 *
 * @author _Ken.Hu
 */
public enum Jackson {
    ;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }

    private static final ObjectWriter writer = OBJECT_MAPPER.writer();
    private static final ObjectWriter prettyWriter = OBJECT_MAPPER.writerWithDefaultPrettyPrinter();

    private static final TypeReference<HashMap<String, String>>
            STRING_MAP_TYPEREFERENCE = new TypeReference<HashMap<String, String>>() {
    };

    public static String toJsonPrettyString(Object value) {
        try {
            return prettyWriter.writeValueAsString(value);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }


    public static String toJsonString(Object value) {
        try {
            return writer.writeValueAsString(value);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static <T> T fromJsonString(String json, TypeReference<T> valueTypeRef) {
        if (json == null) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(json, valueTypeRef);
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to parse Json String.", e);
        }
    }

    public static <T> T fromJsonString(URL url, TypeReference<T> valueTypeRef) {
        try {
            return OBJECT_MAPPER.readValue(url, valueTypeRef);
        } catch (IOException e) {
            throw new IllegalArgumentException("Unable to parse Json String.", e);
        }
    }

    /**
     * Returns the deserialized object from the given json string and target
     * class; or null if the given json string is null.
     */
    public static <T> T fromJsonString(String json, Class<T> clazz) {
        if (json == null) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to parse Json String.", e);
        }
    }

    /**
     * Returns a map of strings from the given json string; or null if the given json string is null.
     */
    public static Map<String, String> stringMapFromJsonString(String json) {
        if (json == null) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(json, STRING_MAP_TYPEREFERENCE);
        } catch (IOException e) {
            throw new IllegalArgumentException("Unable to parse Json String.", e);
        }
    }

    /**
     * Returns the deserialized object from the given json string and target
     * class; or null if the given json string is null. Clears the JSON location in the event of an error
     */
    public static <T> T fromSensitiveJsonString(String json, Class<T> clazz) {
        if (json == null) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to parse Json String.", e);
        }
    }

    public static <T> T jsonNodeParseToObject(JsonNode jsonNode, Class<T> clazz) {
        return OBJECT_MAPPER.convertValue(jsonNode, clazz);
    }

    public static JsonNode jsonNodeParse(Object obj) {
        return fromJsonString(Jackson.toJsonString(obj), JsonNode.class);
    }

    public static JsonNode jsonNodeOf(String json) {
        return fromJsonString(json, JsonNode.class);
    }

    public static JsonGenerator jsonGeneratorOf(Writer writer) throws IOException {
        return new JsonFactory().createGenerator(writer);
    }

    public static <T> T loadFrom(File file, Class<T> clazz) throws IOException {
        try {
            return OBJECT_MAPPER.readValue(file, clazz);
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    public static ObjectWriter getWriter() {
        return writer;
    }

    public static ObjectWriter getPrettyWriter() {
        return prettyWriter;
    }
}