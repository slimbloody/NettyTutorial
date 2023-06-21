package com.slimbloody.lightning.simple.protocol.res;

import com.slimbloody.lightning.simple.protocol.Command;
import com.slimbloody.lightning.simple.protocol.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoinGroupResponsePacket extends Packet {
  private String groupId;
  private boolean success;
  private String reason;
  
  @Override
  public byte getCommand() {
    return Command.JOIN_GROUP_RESPONSE;
  }
}
