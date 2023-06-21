package com.slimbloody.lightning.simple.protocol.req;

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
public class ListGroupMembersRequestPacket extends Packet {
  private String groupId;
  
  @Override
  public byte getCommand() {
    return Command.LIST_GROUP_MEMBERS_REQUEST;
  }
}
