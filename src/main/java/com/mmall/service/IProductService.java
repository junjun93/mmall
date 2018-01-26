package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.vo.ProductDetailVo;
import com.mmall.vo.ProductListVo;

import java.util.List;

/**
 * @author junjun
 * @date 2018/1/24
 **/
public interface IProductService {

    ServerResponse<PageInfo> getManageList(Integer pageNum, Integer pageSize);

    ServerResponse<PageInfo> searchProduct(String productName, Integer productId, Integer pageNum, Integer pageSize);

    ServerResponse<ProductDetailVo> getManageDetail(Integer productId);

    ServerResponse<String> saveOrUpdateProduct(Product product);

    ServerResponse<String> setSaleStatus(Integer productId, Integer status);

    ServerResponse<PageInfo> getProductList(String keyword, Integer categoryId, Integer pageNum, Integer pageSize, String orderBy);

    ServerResponse<ProductDetailVo> getProductDetail(Integer productId);
}
