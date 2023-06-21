package com.slimbloody.lightning.simple.serializer;

public interface Serializer {
  Serializer DEFAULT = new JsonSerializer();
  
  byte getSerializerAlgorithm();
  byte[] serialize(Object object);
  <T> T deserialize(Class<T> clazz, byte[] bytes);
}
