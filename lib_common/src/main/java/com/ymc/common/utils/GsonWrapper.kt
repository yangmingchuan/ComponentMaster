package com.ymc.common.utils

import android.text.TextUtils
import org.json.JSONArray
import org.json.JSONObject

/**
 * gson 包装
 * @author ymc
 */
object GsonWrapper {

    private const val LINE_BORDER = "═════════════════════════" +
            "══════════════════════════════════════════"

    /**
     * 格式化 log
     */
    fun formatJsonToLog(log: String) = if (!LogUtils.isEnable() || !isJson(
            log
        )
    ) {
        StringBuilder("\t\n╔").append(LINE_BORDER)
                .append("\n║").append(log)
                .append("\n╚").append(LINE_BORDER).append("\n ").toString()
    } else StringBuilder("\n╔").append(LINE_BORDER)
            .append("\n║").append(log)
            .append("\n║").append(LINE_BORDER)
            .append("\n║").append(
            formatJsonStr(
                log
            )
        )
            .append("\n╚").append(LINE_BORDER).append("\n ").toString()

    private fun getLevelStr(level: Int): String {
        val levelStr = StringBuffer()
        for (i in 0 until level) {
            levelStr.append("    ")
        }
        return levelStr.toString()
    }

    /**
     * 格式化 json
     */
    @JvmStatic
    fun formatJsonStr(jsonData: String): String {
        var jsonStr = jsonData
        if (!isJson(jsonStr)) {
            return jsonStr
        }
        jsonStr = jsonStr.trim { it <= ' ' }
        var level = 0
        var lastChar = ' '
        val jsonFormatStr = StringBuffer()
        val len = jsonStr.length
        for (i in 0 until len) {
            val c = jsonStr[i]
            if (level > 0 && '\n' == jsonFormatStr[jsonFormatStr.length - 1]) {
                jsonFormatStr.append(
                    getLevelStr(
                        level
                    )
                )
            }
            when (c) {
                '{', '[' -> {
                    if (lastChar != ',') {
                        jsonFormatStr.append(
                            getLevelStr(
                                level
                            )
                        )
                    }
                    jsonFormatStr.append(c).append("\n║")
                    level++
                }

                ',' -> jsonFormatStr.append(c)
                        .append("\n║")
                        .append(
                            getLevelStr(
                                level
                            )
                        )

                '}', ']' -> {
                    level--
                    jsonFormatStr.append("\n║")
                            .append(
                                getLevelStr(
                                    level
                                )
                            )
                            .append(c)
                }

                else -> {
                    if (lastChar == '[' || lastChar == '{') {
                        jsonFormatStr.append(
                            getLevelStr(
                                level
                            )
                        )
                    }
                    jsonFormatStr.append(c)
                }
            }
            lastChar = c
        }
        return jsonFormatStr.toString()
    }

    /**
     * 判断字符串是否为 json 格式
     */
    @JvmStatic
    inline fun isJson(json: String): Boolean {
        var json = json
        if (TextUtils.isEmpty(json)) {
            return false
        }
        json = json.trim { it <= ' ' }
        try {
            if (json.startsWith("[")) {
                JSONArray(json)
                return true
            } else if (json.startsWith("{")) {
                JSONObject(json)
                return true
            }
        } catch (e: Exception) {
        }
        return false
    }

}