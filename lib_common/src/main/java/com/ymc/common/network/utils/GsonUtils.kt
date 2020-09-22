package com.ymc.common.network.utils

import com.google.gson.*
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException
import java.lang.reflect.Type

/**
 * Created by ymc on 2020/9/22.
 * @Description Gson 工具类
 */
object GsonUtils {

    val gson: Gson by lazy {
        GsonBuilder()
            .registerTypeAdapter(Long::class.java, LongDefault0Adapter())
            .registerTypeAdapter(Long::class.javaPrimitiveType, LongDefault0Adapter())
            .registerTypeAdapter(Double::class.java, DoubleDefault0Adapter())
            .registerTypeAdapter(Double::class.javaPrimitiveType, DoubleDefault0Adapter())
            .registerTypeAdapter(Int::class.javaPrimitiveType, IntegerDefault0Adapter())
            .registerTypeAdapter(Int::class.java, IntegerDefault0Adapter())
            .registerTypeAdapter(String::class.java, StringNullAdapter())
//                .registerTypeAdapterFactory(NullStringToEmptyAdapterFactory<Any?>())
            .disableHtmlEscaping()
            .create()
    }

    /**
     * Long 适配
     */
    class LongDefault0Adapter : JsonSerializer<Long?>, JsonDeserializer<Long> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Long {
            try {
                if ("" == json.asString || "null" == json.asString) {
                    //定义为long类型,如果后台返回""或者null,则返回0
                    return 0L
                }
            } catch (ignore: Exception) {
            } finally {
                return try {
                    json.asLong
                } catch (e: NumberFormatException) {
                    throw JsonSyntaxException(e)
                }
            }
        }

        override fun serialize(src: Long?, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
            return JsonPrimitive(src)
        }
    }

    /**
     * Double 适配
     */
    class DoubleDefault0Adapter : JsonSerializer<Double?>, JsonDeserializer<Double> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Double {
            try {
                if ("" == json.asString || "null" == json.asString) {
                    //定义为double类型,如果后台返回""或者null,则返回0.00
                    return 0.00
                }
            } catch (ignore: Exception) {
            } finally {
                return try {
                    json.asDouble
                } catch (e: NumberFormatException) {
                    throw JsonSyntaxException(e)
                }
            }
        }

        override fun serialize(src: Double?, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
            return JsonPrimitive(src)
        }
    }

    /**
     * int 适配
     */
    class IntegerDefault0Adapter : JsonSerializer<Int?>, JsonDeserializer<Int> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Int {
            try {
                if ("" == json.asString || "null" == json.asString) {
                    // /定义为int类型,如果后台返回""或者null,则返回0
                    return 0
                }
            } catch (ignore: Exception) {
            } finally {
                return try {
                    json.asInt
                } catch (e: NumberFormatException) {
                    throw JsonSyntaxException(e)
                }
            }
        }

        override fun serialize(src: Int?, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
            return JsonPrimitive(src)
        }
    }

    /**
     * String 适配
     */
    class StringNullAdapter : TypeAdapter<String?>() {
        @Throws(IOException::class)
        override fun read(reader: JsonReader): String? {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull()
                return ""
            }
            return reader.nextString()
        }

        @Throws(IOException::class)
        override fun write(writer: JsonWriter, value: String?) {
            if (value == null) {
                writer.nullValue()
                return
            }
            writer.value(value)
        }
    }

}