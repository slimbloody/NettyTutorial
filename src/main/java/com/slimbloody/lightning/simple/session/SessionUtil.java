package com.slimbloody.lightning.simple.session;

import com.slimbloody.lightning.simple.exceptions.ChannelGroupNotExistException;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelGroupFuture;
import io.netty.channel.group.ChannelGroupFutureListener;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class SessionUtil {
  private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();
  
  private static final Map<String, ChannelGroup> groupIdChannelGroupMap = new ConcurrentHashMap<>();
  
  private static final Map<String, Set<String>> groupIdMembersMap = new ConcurrentHashMap<>();
  
  public static void bindSession(Session session, Channel channel) {
    userIdChannelMap.put(session.getUid(), channel);
    channel.attr(Attributes.SESSION).set(session);
  }
  
  public static void unBindSession(Channel channel) {
    if (hasLogin(channel)) {
      Session session = getSession(channel);
      userIdChannelMap.remove(session.getUid());
      channel.attr(Attributes.SESSION).set(null);
      log.info(session.getUid() + " logout");
    }
  }
  
  public static boolean hasLogin(Channel channel) {
    return getSession(channel) != null;
  }
  
  public static Session getSession(Channel channel) {
    return channel.attr(Attributes.SESSION).get();
  }
  
  public static Channel getChannel(String uid) {
    return userIdChannelMap.get(uid);
  }
  
  public static void bindChannelGroup(String groupId, ChannelGroup channelGroup) {
    groupIdChannelGroupMap.put(groupId, channelGroup);
  }
  
  public static void joinChannelGroup(String groupId, Channel channel)
    throws IOException {
    ChannelGroup channelGroup = getChannelGroup(groupId);
    if (Objects.isNull(channelGroup)) {
      throw new ChannelGroupNotExistException();
    }
  
    String uid = getSession(channel).getUid();
    channelGroup.add(channel);
    Set<String> groupIdMembers = Optional
      .ofNullable(groupIdMembersMap.get(groupId))
      .orElseGet(HashSet::new);

    if (groupIdMembers.isEmpty()) {
      groupIdMembersMap.put(groupId, groupIdMembers);
    }
    groupIdMembers.add(uid);
  }
  
  public static void quitChannelGroup(String groupId, Channel channel)
    throws IOException {
    ChannelGroup channelGroup = Optional
      .ofNullable(getChannelGroup(groupId))
      .orElseThrow(ChannelGroupNotExistException::new);
    channelGroup.remove(channel);
  
    String uid = getSession(channel).getUid();;
    Set<String> groupIdMembersMap = SessionUtil.groupIdMembersMap.get(groupId);
    groupIdMembersMap.remove(uid);
    
    // channelGroup没引用了,会被自动清理
    if (groupIdMembersMap.isEmpty()) {
      // todo:这里是不是要改成同步合适,至少要加锁
      channelGroup.newCloseFuture().addListener(
        new ChannelGroupFutureListener() {
          @Override
          public void operationComplete(ChannelGroupFuture future)
            throws Exception {
            groupIdChannelGroupMap.remove(groupId);
            groupIdMembersMap.remove(groupId);
          }
        });
    }
  }
  
  public static void unbindChannelGroup(String groupId) {
    groupIdChannelGroupMap.remove(groupId);
    groupIdMembersMap.remove(groupId);
  }
  
  public static ChannelGroup getChannelGroup(String groupId) {
    return groupIdChannelGroupMap.get(groupId);
  }
  
  public static Set<String> getChannelGroupMembers(String groupId) {
    return groupIdMembersMap.get(groupId);
  }
}
