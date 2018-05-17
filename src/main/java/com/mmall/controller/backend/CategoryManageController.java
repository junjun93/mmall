package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisShardedPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author junjun
 * @date 2018/1/23
 **/
@Controller
@RequestMapping("/manage/category/")
public class CategoryManageController {

    @Autowired
    private ICategoryService iCategoryService;
    /**
     * 1.获取品类子节点(平级)
     * */
    @RequestMapping("get_category.do")
    @ResponseBody
    public ServerResponse<List<Category>> getCategory(HttpServletRequest httpServletRequest, @RequestParam(value = "categoryId",
        defaultValue = "0") Integer categoryId){
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录，无法获取用户信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr, User.class);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        //if(iUserService.checkAdminRole(user).isSuccess()){
        if(user.getRole() == Const.Role.ROLE_ADMIN){
            return iCategoryService.getCategory(categoryId);
        }
        return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
    }

    /**
     * 2.增加节点
     * */
    @RequestMapping("add_category.do")
    @ResponseBody
    public ServerResponse<String> addCategory(HttpServletRequest httpServletRequest, String categoryName,
                                              @RequestParam(value = "parentId", defaultValue = "0") Integer parentId){
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录，无法获取用户信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr, User.class);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    "用户未登录，请登录");
        }
        if(user.getRole() == Const.Role.ROLE_ADMIN){
            return iCategoryService.addCategory(categoryName, parentId);
        }
        return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
    }

    /**
     * 3.修改品类名字
     * */
    @RequestMapping("set_category_name.do")
    @ResponseBody
    public ServerResponse<String> setCategoryName(HttpServletRequest httpServletRequest, String categoryName,
        @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId){
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录，无法获取用户信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr, User.class);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    "用户未登录，请登录");
        }
        if(user.getRole() == Const.Role.ROLE_ADMIN){
            return iCategoryService.setCategoryName(categoryName, categoryId);
        }
        return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
    }

    /**
     * 4.获取当前分类id及递归子节点categoryId
     * */
    @RequestMapping("get_deep_category.do")
    @ResponseBody
    public ServerResponse<List<Integer>> getDeepCategory(HttpServletRequest httpServletRequest, @RequestParam(value = "categoryId",
            defaultValue = "0") Integer categoryId){
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录，无法获取用户信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr, User.class);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        if(user.getRole() == Const.Role.ROLE_ADMIN){
            return iCategoryService.getDeepCategory(categoryId);
        }
        return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
    }
}
