package com.mmall.service;

import com.mmall.common.ServerResponse;

/**
 * @author junjun
 * @date 2018/2/17
 **/
public interface IOrderService {

    public ServerResponse createOrder(Integer userId, Integer shippingId);
}
