package com.slimbloody.lightning.simple.session;

import java.util.concurrent.atomic.AtomicInteger;

public class GroupIdGenerator {
  public static final AtomicInteger counter = new AtomicInteger(100);

  public static String genGroupId() {
    return String.valueOf(counter.getAndIncrement());
  }
}
