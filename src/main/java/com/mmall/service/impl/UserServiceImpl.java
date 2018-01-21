package com.mmall.service.impl;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * 5.忘记密码，用户名是否存在、密保问题是否存在
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
            //Cache_Token
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByErrorMessage("问题的答案错误");
    }

}
