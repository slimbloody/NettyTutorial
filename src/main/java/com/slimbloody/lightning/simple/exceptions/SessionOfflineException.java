package com.slimbloody.lightning.simple.exceptions;

import java.io.IOException;

public class SessionOfflineException extends IOException {
  public SessionOfflineException() {
    super("session off line");
  }
}
