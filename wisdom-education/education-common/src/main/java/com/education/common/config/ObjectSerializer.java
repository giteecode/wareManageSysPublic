package com.education.common.config;

import org.springframework.data.redis.serializer.RedisSerializer;

import java.io.*;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/14 19:55
 */
public class ObjectSerializer implements RedisSerializer<Object> {

    public static final int BYTE_ARRAY_OUTPUT_STREAM_SIZE = 128;

    @Override
    public byte[] serialize(Object object)  {
        byte[] result = new byte[0];

        if (object == null) {
            return result;
        }
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(BYTE_ARRAY_OUTPUT_STREAM_SIZE);
        if (!(object instanceof Serializable)) {

        }
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
            result =  byteStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Object deserialize(byte[] bytes)  {
        Object result = null;

        if (bytes == null || bytes.length == 0) {
            return null;
        }

        try {
            ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new MultiClassLoaderObjectInputStream(byteStream);
            result = objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
        }

        return result;
    }


}
