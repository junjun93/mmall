package com.mmall.service.impl;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.common.TokenCache;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * @author junjun
 **/
@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 1.用户登录
     * */
    @Override
    public ServerResponse<User> login(String username, String password) {
        int resultCount = userMapper.checkUsername(username);
        if(resultCount == 0){
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        User user = userMapper.selectLogin(username, password);
        if(user == null){
            return ServerResponse.createByErrorMessage("密码错误");
        }

        user.setPassword(EMPTY);
        return ServerResponse.createBySuccess("登录成功", user);
    }

    /**
     * 2.用户注册，判断用户名、邮箱，分配权限、密码加密
     * */
    @Override
    public ServerResponse<String> register(User user){
        //复用校验方法，校验失败用户名存在
        ServerResponse volidResponse = this.checkValid(user.getUsername(), Const.USERNAME);
        if(!volidResponse.isSuccess()){
            return volidResponse;
        }
        volidResponse = this.checkValid(user.getEmail(), Const.EMAIL);
        if(!volidResponse.isSuccess()){
            return volidResponse;
        }
        /*int resultCount = userMapper.checkUsername(user.getUsername());
        if(resultCount > 0){
            return ServerResponse.createByErrorMessage("用户名已存在");
        }
        resultCount = userMapper.checkEmail(user.getEmail());
        if(resultCount > 0){
            return ServerResponse.createByErrorMessage("email已存在");
        }*/
        //用户权限
        user.setRole(Const.Role.ROLE_CUSTOMER);
        //md5加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));

        int resultCount = userMapper.insert(user);
        if(resultCount == 0){
            return ServerResponse.createByErrorMessage("注册失败");
        }
        return ServerResponse.createBySuccess("注册成功");
    }

    /**
     * 3.检查用户名是否有效，两个字段校验共用type,要做判断
     * */
    @Override
    public ServerResponse<String> checkValid(String str, String type){
        if(StringUtils.isNotBlank(type)) {
            if(Const.USERNAME.equals(type)) {
                int resultCount = userMapper.checkUsername(str);
                if(resultCount > 0){
                    return ServerResponse.createByErrorMessage("用户已存在");
                }
            }
            if(Const.EMAIL.equals(type)) {
                int resultCount = userMapper.checkEmail(str);
                if(resultCount > 0){
                    return ServerResponse.createByErrorMessage("email已存在");
                }
            }
        }else{
            return ServerResponse.createByErrorMessage("参数错误");
        }
        //用户名、email都可以用
        return ServerResponse.createBySuccess("校验成功");
    }

    /**
     * 5.忘记密码，用户名是否存在(复用)、密保问题是否存在
     * */
    @Override
    public ServerResponse<String> selectQuestion(String username){
        //复用校验方法
        ServerResponse validResponse = this.checkValid(username, Const.USERNAME);
        if(validResponse.isSuccess()){
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        String question = userMapper.selectQuestionByUsername(username);
        if(StringUtils.isNotBlank(question)){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByErrorMessage("找回密码的问题是空的");
    }


    /**
     * 6.提交问题答案
     * */
    @Override
    public ServerResponse<String> forgetCheckAnswer(String username, String question, String answer){
        int resultCount = userMapper.checkAnswer(username, question, answer);
        if(resultCount > 0){
            //说明问题、问题答案是这个用户的，并且是正确的
            String forgetToken = UUID.randomUUID().toString();
            //key通过用户名拼接，确保key唯一
            TokenCache.setKey(TokenCache.TOKEN_PREFIX+username, forgetToken);
            return ServerResponse.createBySuccess(forgetToken);
        }
        return ServerResponse.createByErrorMessage("问题的答案错误");
    }

    /**
     * 7.忘记密码的重设密码，token是否存在、用户名是否存在(复用)、token是否失效
     * */
    @Override
    public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken){
        if(StringUtils.isBlank(forgetToken)){
            return ServerResponse.createByErrorMessage("token不存在，需要传递");
        }
        ServerResponse volidResponse = this.checkValid(username, Const.USERNAME);
        if(volidResponse.isSuccess()){
            return ServerResponse.createBySuccess("用户名不存在");
        }
        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX+username);
        if(StringUtils.isBlank(token)){
            return ServerResponse.createByErrorMessage("token失效或过期");
        }
        if(StringUtils.equals(forgetToken, token)){
            String MD5Password = MD5Util.MD5EncodeUtf8(passwordNew);
            int ResultCount = userMapper.updatePasswordByUsername(username, MD5Password);
            if(ResultCount > 0){
                return ServerResponse.createBySuccess("修改密码成功");
            }
        }else{
            return ServerResponse.createByErrorMessage("token错误，请重新获取重置密码的token");
        }
        return ServerResponse.createByErrorMessage("修改密码失败");
    }

    /**
     * 8.登录中状态重置密码，判断旧密码、更新密码
     * */
    @Override
    public ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user){
        //防止横向越权，校验旧密码指定这个用户
        int ResultCount = userMapper.CheckPassword(MD5Util.MD5EncodeUtf8(passwordOld), user.getId());
        if(ResultCount == 0){
            return ServerResponse.createByErrorMessage("旧密码错误");
        }
        ResultCount = userMapper.updatePasswordByUsername(MD5Util.MD5EncodeUtf8(passwordNew), user.getUsername());
        /* user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
           int updateCount = userMapper.updateByPrimaryKeySelective(user);
        * */
        if(ResultCount > 0){
            return ServerResponse.createBySuccessMessage("更新密码成功");
        }
        return ServerResponse.createByErrorMessage("更新密码失败");
    }
}
