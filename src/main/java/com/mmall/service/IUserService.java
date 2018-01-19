package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;

/**
 * @author junjun
 * @date 2018/1/19
 **/
public interface IUserService {
    ServerResponse<User> login(String username, String password);
}
