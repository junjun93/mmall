package com.mmall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.service.IProductService;
import com.mmall.vo.ProductDetailVo;
import com.mmall.vo.ProductListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author junjun
 * @date 2018/1/25
 **/
@Controller
@RequestMapping("/product/")
public class ProductController {

    @Autowired
    private IProductService iProductService;
    /**
     * 1.产品搜索及动态排序
     * */
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<PageInfo> getProductList(@RequestParam(value = "keyword", required = false) String keyword,
                                                   @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                   @RequestParam(value = "orderBy", defaultValue = "") String orderBy){
        return iProductService.getProductList(keyword, categoryId, pageNum, pageSize, orderBy);
    }

    /**
     * 2.产品详情
     * */
    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse<ProductDetailVo> getProductDetail(Integer productId){
        return iProductService.getProductDetail(productId);
    }
}
