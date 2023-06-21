package com.slimbloody.lightning.simple.protocol.req;


import com.slimbloody.lightning.simple.protocol.Command;
import com.slimbloody.lightning.simple.protocol.Packet;

public class HeartBeatRequestPacket extends Packet {
  @Override
  public byte getCommand() {
    return Command.HEARTBEAT_REQUEST;
  }
}
