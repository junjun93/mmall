package com.mmall.service.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CartMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Cart;
import com.mmall.pojo.Product;
import com.mmall.service.ICartService;
import com.mmall.util.BigDecimalUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.CartProductVo;
import com.mmall.vo.CartVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author junjun
 * @date 2018/1/26
 **/
@Service("iCartService")
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    /**
     * 1.购物车列表
     * */
    @Override
    public ServerResponse<CartVo> list(Integer userId){
        CartVo cartVo = this.getCartVoLimit(userId);
        return ServerResponse.createBySuccess(cartVo);
    }

    /**
     * 2.购物车添加商品
     * */
    @Override
    public ServerResponse<CartVo> add(Integer userId, Integer count, Integer productId) {
        if (count == null || productId == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Cart cart = cartMapper.selectCartByUserIdProductId(userId, productId);
        //产品不在购物车里，需要新增记录
        if (cart == null) {
            Cart cartItem = new Cart();
            cartItem.setUserId(userId);
            cartItem.setQuantity(count);
            cartItem.setProductId(productId);
            cartItem.setChecked(Const.Cart.CHECKED);
            cartMapper.insert(cartItem);
        } else {
            //产品在购物车里，需要增加数量。没有else，cart为空时就会NullPointer
            count = cart.getQuantity() + count;
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKeySelective(cart);
            CartVo cartVo = this.getCartVoLimit(userId);
            return ServerResponse.createBySuccess(cartVo);
        }
        return this.list(userId);
    }

    /**
     * 购物车共用方法
     * */
    private CartVo getCartVoLimit(Integer userId){
        CartVo cartVo = new CartVo();
        List<Cart> cartList = cartMapper.selectCartByUserId(userId);
        List<CartProductVo> cartProductVoList = Lists.newArrayList();

        BigDecimal cartTotalPrice = new BigDecimal("0");

        //计算同一类商品总价，遍历数据写入VO
        if(CollectionUtils.isNotEmpty(cartList)){
            for(Cart cartItem : cartList){
                CartProductVo cartProductVo = new CartProductVo();
                cartProductVo.setId(cartItem.getId());
                cartProductVo.setUserId(userId);
                cartProductVo.setProductId(cartItem.getProductId());

                Product product = productMapper.selectByPrimaryKey(cartItem.getProductId());
                if(product != null) {
                    cartProductVo.setProductName(product.getName());
                    cartProductVo.setProductSubtitle(product.getSubtitle());
                    cartProductVo.setProductMainImage(product.getMainImage());
                    cartProductVo.setProductPrice(product.getPrice());
                    cartProductVo.setProductStock(product.getStock());
                    cartProductVo.setProductStatus(product.getStatus());

                    //判断库存，怎么没更新库存
                    int buyLimitCount = 0;
                    if (product.getStock() >= cartItem.getQuantity()) {
                        buyLimitCount = cartItem.getQuantity();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
                    } else {
                        buyLimitCount = product.getStock();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
                        //购物车中更新有效库存
                        Cart cartForQuantity = new Cart();
                        cartForQuantity.setId(cartItem.getId());
                        cartForQuantity.setQuantity(buyLimitCount);
                        cartMapper.updateByPrimaryKeySelective(cartForQuantity);
                    }
                    cartProductVo.setQuantity(buyLimitCount);
                    //商品总价，VO拿到同一个Quantity
                    cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(),
                            cartProductVo.getQuantity()));
                    cartProductVo.setProductChecked(cartItem.getChecked());
                    if (cartItem.getChecked() == Const.Cart.CHECKED) {
                        //如果已经勾选，增加到整个的购物车总价中
                        cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(),
                                cartProductVo.getProductTotalPrice().doubleValue());
                    }
                    cartProductVoList.add(cartProductVo);
                }
            }
            cartVo.setCartTotalPrice(cartTotalPrice);
            cartVo.setCartProductVoList(cartProductVoList);
            cartVo.setAllChecked(this.getAllCheckedStatus(userId));
            cartVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));//??
        }
        return cartVo;
    }

    private boolean getAllCheckedStatus(Integer userId){
        if(userId == null){
            return false;
        }
        return cartMapper.selectCartProductCheckedStatusByUserId(userId) == 0;
    }
    /**
     * 3.购物车更新商品数量
     * */
    @Override
    public ServerResponse<CartVo> update(Integer userId, Integer count, Integer productId) {
        if(count == null || productId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Cart cart = cartMapper.selectCartByUserIdProductId(userId, productId);
        if(cart != null){
            cart.setQuantity(count);
        }
        cartMapper.updateByPrimaryKeySelective(cart);
        return this.list(userId);
    }

    /**
     * 4.购物车移除商品
     * */
    @Override
    public ServerResponse<CartVo> deleteProduct(Integer userId, String productIds){
        List<String> productIdList = Splitter.on(",").splitToList(productIds);
        if(CollectionUtils.isEmpty(productIdList)){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        cartMapper.deleteByUserIdProductIds(userId, productIdList);
        return this.list(userId);
    }

    /**
     * 5.购物车查询商品数量
     * */
    @Override
    public ServerResponse<Integer> getCartProductCount(Integer userId){
        if(userId == null){
            return ServerResponse.createBySuccess(0);
        }
        int count = cartMapper.selectCartProductCount(userId);
        return ServerResponse.createBySuccess(count);
    }

    /**
     * 6-9.购物车各种选择
     * */
    @Override
    public ServerResponse<CartVo> selectOrUnSelect(Integer userId, Integer checked, Integer productId){
        cartMapper.checkedOrUncheckedProduct(userId, checked, productId);
        return this.list(userId);
    }
}
