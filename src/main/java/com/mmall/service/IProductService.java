package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;

/**
 * @author junjun
 * @date 2018/1/24
 **/
public interface IProductService {

    ServerResponse<String> getDetail(Integer productId);

    ServerResponse<String> saveOrUpdateProduct(Product product);

    ServerResponse<String> setSaleStatus(Integer productId, Integer status);
}
