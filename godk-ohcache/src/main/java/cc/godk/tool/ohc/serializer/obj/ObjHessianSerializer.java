package cc.godk.tool.ohc.serializer.obj;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.google.common.base.Charsets;
import org.caffinitas.ohc.CacheSerializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

/**
 * 序列化 反序列化工具
 *
 * @author weitao47
 * @project project-tool
 * @date 2022/9/21 14:22
 */
public class ObjHessianSerializer<T extends Object> implements CacheSerializer<T> {


    @Override
    public void serialize(T t, ByteBuffer byteBuffer) {
        byte[] data = null;
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            Hessian2Output output = new Hessian2Output(os);
            output.writeObject(t);
            output.getBytesOutputStream().flush();
            output.completeMessage();
            output.close();
            data = os.toByteArray();
            byteBuffer.put((byte) ((data.length >>> 8) & 0xFF));
            byteBuffer.put((byte) ((data.length) & 0xFF));
            byteBuffer.put(data);
        } catch (Exception e) {
            // 序列化异常
            throw new RuntimeException(e);
        }
    }

    @Override
    public T deserialize(ByteBuffer byteBuffer) {
        int length = (((byteBuffer.get() & 0xff) << 8) + ((byteBuffer.get() & 0xff)));
        byte[] bytes = new byte[length];
        // 读取字节数组
        byteBuffer.get(bytes);
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        Hessian2Input input = new Hessian2Input(bis);
        try {
            T data = (T) input.readObject();
            return data;
        } catch (Exception e) {
            // 反序列化异常
            throw new RuntimeException(e);
        }
    }

    @Override
    public int serializedSize(T t) {
        byte[] data = null;
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            Hessian2Output output = new Hessian2Output(os);
            output.writeObject(t);
            output.getBytesOutputStream().flush();
            output.completeMessage();
            output.close();
            data = os.toByteArray();
            if (data.length > 65536) {
                throw new RuntimeException("encoded string too long: " + data.length + " bytes");
            }
            return data.length + 2;
        } catch (Exception e) {
            // 序列化异常
            throw new RuntimeException(e);
        }
    }
}
