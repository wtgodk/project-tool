package cc.godk.tool.ohc.serializer.string;

import com.google.common.base.Charsets;
import org.caffinitas.ohc.CacheSerializer;

import java.nio.ByteBuffer;

/**
 *
 *  序列化 反序列化工具
 * @author godk
 * @project project-tool
 * @date 2022/9/21 14:22
 */
public class StringSerializer implements CacheSerializer<String> {

    /**
     * 将字符串对象序列化到 ByteBuffer 中，ByteBuffer是OHC管理的堆外内存区域的映射。
     * @param value
     * @param byteBuffer
     */
    @Override
    public void serialize(String value, ByteBuffer byteBuffer) {
        // 得到字符串对象UTF-8编码的字节数组
        byte[] bytes = value.getBytes(Charsets.UTF_8);
        // 用前16位记录数组长度
        byteBuffer.put((byte) ((bytes.length >>> 8) & 0xFF));
        byteBuffer.put((byte) ((bytes.length) & 0xFF));
        byteBuffer.put(bytes);
    }

    /**
     * 对堆外缓存的字符串进行反序列化
     * @param byteBuffer
     * @return
     */
    @Override
    public String deserialize(ByteBuffer byteBuffer) {
         // 判断字节数组的长度
        int length = (((byteBuffer.get() & 0xff) << 8) + ((byteBuffer.get() & 0xff)));
        byte[] bytes = new byte[length];
        // 读取字节数组
        byteBuffer.get(bytes);
        // 返回字符串对象
        return new String(bytes, Charsets.UTF_8);
    }

    /**
     *  计算字符串序列化后占用的空间
     * @param value
     * @return
     */
    @Override
    public int serializedSize(String value) {
        byte[] bytes = value.getBytes(Charsets.UTF_8);
        // 设置字符串长度限制，2^16 = 65536
        if (bytes.length > 65536){
            throw new RuntimeException("encoded string too long: " + bytes.length + " bytes");
        }
        // 设置字符串长度限制，2^16 = 65536
        return bytes.length + 2;
    }
}
