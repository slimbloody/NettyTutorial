package com.slimbloody.lightning.simple.exceptions;

import java.io.IOException;

public class ChannelGroupNotExistException extends IOException {
  public ChannelGroupNotExistException() {
    super("group not exist");
  }
}
