package com.slimbloody.lightning.simple.serializer;

import com.alibaba.fastjson2.JSON;

public class JsonSerializer implements Serializer {
  
  @Override
  public byte getSerializerAlgorithm() {
    return SerializerAlgorithm.JSON;
  }
  
  @Override
  public byte[] serialize(Object object) {
    return JSON.toJSONBytes(object);
  }
  
  @Override
  public <T> T deserialize(Class<T> clazz, byte[] bytes) {
    return JSON.parseObject(bytes, clazz);
  }
}
