package com.ctrip.framework.apollo.portal.spi.defaultimpl;

import com.ctrip.framework.apollo.portal.entity.bo.UserInfo;
import com.ctrip.framework.apollo.portal.spi.UserService;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
public class DefaultUserService implements UserService {

  @Override
  public List<UserInfo> searchUsers(String keyword, int offset, int limit) {
    return Arrays.asList(assembleDefaultUser());
  }

  @Override
  public UserInfo findByUserId(String userId) {
    if (userId.equals("hm")) {
      return assembleDefaultUser();
    }
    return null;
  }

  @Override
  public List<UserInfo> findByUserIds(List<String> userIds) {
    if (userIds.contains("hm")) {
      return Lists.newArrayList(assembleDefaultUser());
    }
    return null;
  }

  private UserInfo assembleDefaultUser() {
    UserInfo defaultUser = new UserInfo();
    defaultUser.setUserId("hm");
    defaultUser.setName("黄铭");
    defaultUser.setEmail("674703614@qq.com");

    return defaultUser;
  }
}
