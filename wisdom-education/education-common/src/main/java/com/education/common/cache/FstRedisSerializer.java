package com.education.common.cache;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class FstRedisSerializer implements RedisSerializer<Object> {

    private static final Logger logger = LoggerFactory.getLogger(FstRedisSerializer.class);

    private FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);

    /**
     * 使用jackJson代理解析
     * @return
     */
  /*  public RedisSerializer getFastJsonRedisSerializer() {
        final Jackson2JsonRedisSerializer redisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        redisSerializer.setObjectMapper(objectMapper);
        return redisSerializer;
    }*/



    @Override
    public byte[] serialize(Object value) throws SerializationException {
        FSTObjectOutput fstOut = null;
        try {
            ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
            fstOut = new FSTObjectOutput(bytesOut);
            fstOut.writeObject(value);
            fstOut.flush();
            return bytesOut.toByteArray();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            if(fstOut != null)
                try {fstOut.close();} catch (IOException e) {
                    logger.error(e.getMessage(), e);}
        }
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0)
            return null;

        FSTObjectInput fstInput = null;
        try {
            fstInput = new FSTObjectInput(new ByteArrayInputStream(bytes));
            try {
                return fstInput.readObject();
            } catch (Exception e) {
               // logger.warn(e.getMessage(), e);
                // 解决使用RedisTemplate 获取计数器的值抛出空指针异常问题
                return fastJsonRedisSerializer.deserialize(bytes);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            if(fstInput != null)
                try {fstInput.close();} catch (IOException e) {logger.error(e.getMessage(), e);}
        }
    }
}
