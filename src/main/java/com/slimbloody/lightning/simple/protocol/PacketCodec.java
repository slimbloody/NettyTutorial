package com.slimbloody.lightning.simple.protocol;

import com.slimbloody.lightning.simple.protocol.req.CreateGroupRequestPacket;
import com.slimbloody.lightning.simple.protocol.req.GroupMessageRequestPacket;
import com.slimbloody.lightning.simple.protocol.req.HeartBeatRequestPacket;
import com.slimbloody.lightning.simple.protocol.req.JoinGroupRequestPacket;
import com.slimbloody.lightning.simple.protocol.req.ListGroupMembersRequestPacket;
import com.slimbloody.lightning.simple.protocol.req.LoginRequestPacket;
import com.slimbloody.lightning.simple.protocol.req.LogoutRequestPacket;
import com.slimbloody.lightning.simple.protocol.req.MessageRequestPacket;
import com.slimbloody.lightning.simple.protocol.req.QuitGroupRequestPacket;
import com.slimbloody.lightning.simple.protocol.res.CreateGroupResponsePacket;
import com.slimbloody.lightning.simple.protocol.res.GroupMessageResponsePacket;
import com.slimbloody.lightning.simple.protocol.res.HeartBeatResponsePacket;
import com.slimbloody.lightning.simple.protocol.res.JoinGroupResponsePacket;
import com.slimbloody.lightning.simple.protocol.res.ListGroupMembersResponsePacket;
import com.slimbloody.lightning.simple.protocol.res.LoginResponsePacket;
import com.slimbloody.lightning.simple.protocol.res.LogoutResponsePacket;
import com.slimbloody.lightning.simple.protocol.res.MessageResponsePacket;
import com.slimbloody.lightning.simple.protocol.res.QuitGroupResponsePacket;
import com.slimbloody.lightning.simple.serializer.JsonSerializer;
import com.slimbloody.lightning.simple.serializer.Serializer;
import com.slimbloody.lightning.simple.serializer.SerializerAlgorithm;
import io.netty.buffer.ByteBuf;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PacketCodec {
  public static final int MAGIC_NUMBER = 0x12345678;
  public static final PacketCodec INSTANCE = new PacketCodec();
  
  private final Map<Byte, Serializer> serializerMap;
  private final Map<Byte, Class<? extends Packet>> packetTypeMap;
  
  public PacketCodec() {
    this.packetTypeMap = new HashMap<>();
    packetTypeMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
    packetTypeMap.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);
    packetTypeMap.put(Command.MESSAGE_REQUEST, MessageRequestPacket.class);
    packetTypeMap.put(Command.MESSAGE_RESPONSE, MessageResponsePacket.class);
    packetTypeMap.put(Command.LOGOUT_REQUEST, LogoutRequestPacket.class);
    packetTypeMap.put(Command.LOGOUT_RESPONSE, LogoutResponsePacket.class);
    packetTypeMap.put(Command.CREATE_GROUP_REQUEST, CreateGroupRequestPacket.class);
    packetTypeMap.put(Command.CREATE_GROUP_RESPONSE, CreateGroupResponsePacket.class);
    packetTypeMap.put(Command.JOIN_GROUP_REQUEST, JoinGroupRequestPacket.class);
    packetTypeMap.put(Command.JOIN_GROUP_RESPONSE, JoinGroupResponsePacket.class);
    packetTypeMap.put(Command.QUIT_GROUP_REQUEST, QuitGroupRequestPacket.class);
    packetTypeMap.put(Command.QUIT_GROUP_RESPONSE, QuitGroupResponsePacket.class);
    packetTypeMap.put(Command.LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestPacket.class);
    packetTypeMap.put(Command.LIST_GROUP_MEMBERS_RESPONSE, ListGroupMembersResponsePacket.class);
    packetTypeMap.put(Command.GROUP_MESSAGE_REQUEST, GroupMessageRequestPacket.class);
    packetTypeMap.put(Command.GROUP_MESSAGE_RESPONSE, GroupMessageResponsePacket.class);
    packetTypeMap.put(Command.HEARTBEAT_REQUEST, HeartBeatRequestPacket.class);
    packetTypeMap.put(Command.HEARTBEAT_RESPONSE, HeartBeatResponsePacket.class);
  
    this.serializerMap = new HashMap<>();
    serializerMap.put(SerializerAlgorithm.JSON, new JsonSerializer());
  }
  
  public Serializer getSerializer(byte serializeAlgorithm) {
    return serializerMap.get(serializeAlgorithm);
  }
  
  public Class<? extends Packet> getRequestType(byte command) {
    return packetTypeMap.get(command);
  }

  public ByteBuf encode(ByteBuf byteBuf, Packet packet) {
    byte[] bytes = Serializer.DEFAULT.serialize(packet);
    byteBuf.writeInt(MAGIC_NUMBER);
    byteBuf.writeByte(packet.getVersion());
    byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
    byteBuf.writeByte(packet.getCommand());
    byteBuf.writeInt(bytes.length);
    byteBuf.writeBytes(bytes);
    return byteBuf;
  }
  
  public Packet decode(ByteBuf byteBuf) {
    // 跳过 magic number
    int magicNumber = byteBuf.readInt();
    if (magicNumber != MAGIC_NUMBER) {
      log.error("magicNumber error " + magicNumber);
      return null;
    }
  
    // 跳过版本号
    byteBuf.skipBytes(1);
    
    byte serializeAlgorithm = byteBuf.readByte();
    byte command = byteBuf.readByte();
    int length = byteBuf.readInt();
    byte[] bytes = new byte[length];
    byteBuf.readBytes(bytes);
    
    Class<? extends  Packet> requestType = getRequestType(command);
    Serializer serializer = getSerializer(serializeAlgorithm);
    
    if (requestType != null && serializer != null) {
      return serializer.deserialize(requestType, bytes);
    }

    return null;
  }
}
