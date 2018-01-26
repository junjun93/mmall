package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.vo.CartVo;

/**
 * @author junjun
 * @date 2018/1/26
 **/
public interface ICartService {

    ServerResponse<CartVo> add(Integer userId, Integer count, Integer productId);
}
