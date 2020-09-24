package com.ymc.common.network.weak

import okio.*
import java.io.OutputStream
import java.nio.ByteBuffer
import java.nio.charset.Charset
import kotlin.math.ceil

/**
 * Created by ymc on 2020/9/24.
 * @Description 自定义BufferedSink
 */
class ByteCountBufferedSink(val mOriginalSink: Sink, byteCount: Long) : BufferedSink {

    private val mByteCount: Long = byteCount
    private val mDelegate: BufferedSink = mOriginalSink.buffer()

    override fun write(source: ByteArray, offset: Int, byteCount: Int): BufferedSink {
        check(isOpen) { "closed" }
        //计算出要写入的次数
        val count = ceil(source.size.toDouble() / mByteCount).toLong()
        for (i in 0 until count) {
            //让每次写入的字节数精确到mByteCount 分多次写入
            val newOffset = i * mByteCount
            val writeByteCount = Math.min(mByteCount, source.size - newOffset)
            buffer().write(source, newOffset.toInt(), writeByteCount.toInt())
            emitCompleteSegments()
        }
        return this
    }

    override fun writeAll(source: Source): Long {
        var totalBytesRead: Long = 0
        var readCount: Long
        while (source.read(buffer(), mByteCount).also { readCount = it } != -1L) {
            totalBytesRead += readCount
            emitCompleteSegments()
        }
        return totalBytesRead
    }

    override val buffer: Buffer
        get() = mDelegate.buffer

    override fun buffer(): Buffer {
        return mDelegate.buffer
    }

    override fun close() {
        mDelegate.close()
    }

    override fun emit(): BufferedSink {
        return mDelegate.emit()
    }

    override fun emitCompleteSegments(): BufferedSink {
        val buffer = buffer()
        mOriginalSink.write(buffer, buffer.size)
        return this
    }

    override fun flush() {
        mDelegate.flush()
    }

    override fun isOpen(): Boolean {
        return mDelegate.isOpen
    }

    override fun outputStream(): OutputStream {
        return mDelegate.outputStream()
    }

    override fun timeout(): Timeout {
        return mDelegate.timeout()
    }

    override fun write(source: ByteArray): BufferedSink {
        return mDelegate.write(source)
    }

    override fun write(byteString: ByteString): BufferedSink {
        return mDelegate.write(byteString)
    }

    override fun write(byteString: ByteString, offset: Int, byteCount: Int): BufferedSink {
        return mDelegate.write(byteString ,offset ,byteCount)
    }

    override fun write(source: Source, byteCount: Long): BufferedSink {
        return mDelegate.write(source,byteCount)
    }

    override fun write(source: Buffer, byteCount: Long) {
        mDelegate.write(source,byteCount)
    }

    override fun write(p0: ByteBuffer?): Int {
        return mDelegate.write(p0)
    }

    override fun writeByte(b: Int): BufferedSink {
        return mDelegate.writeByte(b)
    }

    override fun writeDecimalLong(v: Long): BufferedSink {
        return mDelegate.writeDecimalLong(v)
    }

    override fun writeHexadecimalUnsignedLong(v: Long): BufferedSink {
        return mDelegate.writeHexadecimalUnsignedLong(v)
    }

    override fun writeInt(i: Int): BufferedSink {
        return mDelegate.writeInt(i)
    }

    override fun writeIntLe(i: Int): BufferedSink {
        return mDelegate.writeIntLe(i)
    }

    override fun writeLong(v: Long): BufferedSink {
        return mDelegate.writeLong(v)
    }

    override fun writeLongLe(v: Long): BufferedSink {
        return writeLongLe(v)
    }

    override fun writeShort(s: Int): BufferedSink {
        return writeShort(s)
    }

    override fun writeShortLe(s: Int): BufferedSink {
        return writeShortLe(s)
    }

    override fun writeString(string: String, charset: Charset): BufferedSink {
        return mDelegate.writeString(string,charset)
    }

    override fun writeString(
        string: String,
        beginIndex: Int,
        endIndex: Int,
        charset: Charset
    ): BufferedSink {
        return mDelegate.writeString(string,beginIndex, endIndex, charset)
    }

    override fun writeUtf8(string: String): BufferedSink {
        return mDelegate.writeUtf8(string)
    }

    override fun writeUtf8(string: String, beginIndex: Int, endIndex: Int): BufferedSink {
        return mDelegate.writeUtf8(string, beginIndex, endIndex)
    }

    override fun writeUtf8CodePoint(codePoint: Int): BufferedSink {
        return mDelegate.writeUtf8CodePoint(codePoint)
    }

}