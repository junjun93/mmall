package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.vo.CartVo;

/**
 * @author junjun
 * @date 2018/1/26
 **/
public interface ICartService {

    ServerResponse<CartVo> list(Integer userId);

    ServerResponse<CartVo> add(Integer userId, Integer count, Integer productId);

    ServerResponse<CartVo> update(Integer userId, Integer count, Integer productId);

    ServerResponse<CartVo> deleteProduct(Integer userId, String productIds);

    ServerResponse<CartVo> selectOrUnSelect(Integer userId, Integer checked, Integer productId);

    ServerResponse<Integer> getCartProductCount(Integer userId);

}
