package com.mmall.controller.portal;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @author junjun
 * @date 2018/1/25
 **/
@Controller
@RequestMapping("/product")
public class ProductController {

    public ServerResponse<Product> getProductDetail(HttpSession session, Integer productId){
        return null;
    }
}
