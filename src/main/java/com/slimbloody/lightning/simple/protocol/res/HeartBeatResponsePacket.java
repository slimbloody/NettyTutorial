package com.slimbloody.lightning.simple.protocol.res;

import com.slimbloody.lightning.simple.protocol.Command;
import com.slimbloody.lightning.simple.protocol.Packet;

public class HeartBeatResponsePacket extends Packet {
  @Override
  public byte getCommand() {
    return Command.HEARTBEAT_RESPONSE;
  }
}
