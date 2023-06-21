package com.slimbloody.lightning.simple.protocol.res;

import com.slimbloody.lightning.simple.protocol.Command;
import com.slimbloody.lightning.simple.protocol.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupMessageResponsePacket extends Packet {
  private String fromGroupId;
  private String fromUid;
  private String message;
  
  @Override
  public byte getCommand() {
    return Command.GROUP_MESSAGE_RESPONSE;
  }
}
