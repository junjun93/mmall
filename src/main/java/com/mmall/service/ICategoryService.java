package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;

import java.util.List;

/**
 * @author junjun
 * @date 2018/1/23
 **/
public interface ICategoryService {
    ServerResponse<List<Category>> getCategory(Integer categoryId);

    ServerResponse<String> addCategory(String categoryName, Integer parentId);

    ServerResponse<String> setCategoryName(String categoryName, Integer parentId);

    public ServerResponse<List<Integer>> getDeepCategory(Integer categoryId);
}
