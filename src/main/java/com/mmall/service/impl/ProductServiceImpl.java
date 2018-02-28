package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Category;
import com.mmall.pojo.Product;
import com.mmall.service.ICategoryService;
import com.mmall.service.IProductService;
import com.mmall.util.DateTimeUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.ProductDetailVo;
import com.mmall.vo.ProductListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author junjun
 * @date 2018/1/24
 **/
@Service("iProductService")
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ICategoryService iCategoryService;
    /**
     * 1.产品list
     * */
    @Override
    public ServerResponse<PageInfo> getManageList(Integer pageNum, Integer pageSize){
        //startPage--start
        //填充自己的sql查询逻辑
        //pageHelper-收尾
        PageHelper.startPage(pageNum, pageSize);
        List<Product>productList = productMapper.selectList();

        List<ProductListVo> productListVoList = Lists.newArrayList();
        for(Product productItem : productList){
            //组装VO
            ProductListVo productListVo = assembleProductListVo(productItem);
            productListVoList.add(productListVo);
        }
        //通过数据库查询到POJO用于分页，VO展示需要的查询结果
        PageInfo pageResult = new PageInfo(productList);
        pageResult.setList(productListVoList);
        return ServerResponse.createBySuccess(pageResult);
    }

    private ProductListVo assembleProductListVo(Product product){
        ProductListVo productListVo = new ProductListVo();
        productListVo.setId(product.getId());
        productListVo.setCategoryId(product.getId());
        productListVo.setName(product.getName());
        productListVo.setSubtitle(product.getSubtitle());
        productListVo.setMainImage(product.getMainImage());
        productListVo.setPrice(product.getPrice());
        productListVo.setStatus(product.getStatus());
        //productListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix", "https://img.junjun.cn"));
        return productListVo;
    }

    /**
     * 2.产品搜索
     * */
    @Override
    public ServerResponse<PageInfo> searchProduct(String productName, Integer productId, Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum, pageSize);
        if(StringUtils.isNotBlank(productName)) {
            productName = new StringBuilder().append("%").append(productName).append("%").toString();
        }
        List<Product> productList = productMapper.selectByNameAndProductId(productName, productId);
        List<ProductListVo> productListVoList = Lists.newArrayList();
        for(Product productItem : productList){
            ProductListVo productListVo = assembleProductListVo(productItem);
            productListVoList.add(productListVo);
        }
        PageInfo pageResult = new PageInfo(productList);
        pageResult.setList(productListVoList);
        return ServerResponse.createBySuccess(pageResult);
    }

    /**
     * 4.产品详情，不分上下架
     * */
    @Override
    public ServerResponse<ProductDetailVo> getManageDetail(Integer productId){
        if(productId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),
                ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product == null){
            return ServerResponse.createByErrorMessage("产品已下架或删除");
        }
        ProductDetailVo productDetailVo = assembleProductDetailVo(product);
        return ServerResponse.createBySuccess(productDetailVo);
    }

    private ProductDetailVo assembleProductDetailVo(Product product){
        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo.setId(product.getId());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setName(product.getName());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setSubImages(product.getSubImages());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setStock(product.getStock());
        productDetailVo.setStatus(product.getStatus());

        //productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix", "https://img.junjun.cn"));
        //id不存在或category不存在
        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if(category == null){
            //默认根节点
            productDetailVo.setParentCategoryId(0);
        }
        productDetailVo.setParentCategoryId(category.getParentId());

        productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));

        return productDetailVo;
    }

    /**
     * 5.产品上下架
     * */
    @Override
    public ServerResponse<String> setSaleStatus(Integer productId, Integer status) {
        if(productId == null || status == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),
                ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        int rowCount = productMapper.updateByPrimaryKeySelective(product);
        if(rowCount > 0){
            return ServerResponse.createBySuccessMessage("更新产品销售状态成功");
        }
        return ServerResponse.createByErrorMessage("更新产品销售状态失败");
    }

    /**
     * 6.新增OR更新产品
     * */
    @Override
    public ServerResponse<String> saveOrUpdateProduct(Product product) {
        if(product != null){
            //设置主图
            if(StringUtils.isNotBlank(product.getSubImages())){
                String[] subImagesArray = product.getSubImages().split(",");
                if(subImagesArray.length > 0){
                    product.setMainImage(subImagesArray[0]);
                }
            }

            if(product.getId() != null){
                int rowCount = productMapper.updateByPrimaryKeySelective(product);
                if(rowCount > 0){
                    return ServerResponse.createBySuccessMessage("更新产品成功");
                }else{
                    return ServerResponse.createByErrorMessage("更新产品失败");
                }
            }else{
                int rowCount = productMapper.insert(product);
                if(rowCount > 0){
                    return ServerResponse.createBySuccessMessage("添加产品成功");
                }else{
                    return ServerResponse.createByErrorMessage("添加产品失败");
                }
            }
        }
        return ServerResponse.createByErrorMessage("添加或更新产品参数不正确");
    }

    /**
     * 1.产品搜索及动态排序
     * */
    @Override
    public ServerResponse<PageInfo> getProductList(String keyword, Integer categoryId, Integer pageNum, Integer pageSize, String orderBy){
        if(StringUtils.isBlank(keyword) && categoryId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        List<Integer> categoryIdList = Lists.newArrayList();
        if(categoryId != null){
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            //没有该分类、无关键词，此时不报错，返回一个空的结果集
            if(category == null && StringUtils.isBlank(keyword)){
                PageHelper.startPage(pageNum, pageSize);
                List<ProductDetailVo> productDetailVoList = Lists.newArrayList();
                PageInfo pageInfo = new PageInfo(productDetailVoList);
                return ServerResponse.createBySuccess(pageInfo);
            }
            categoryIdList = iCategoryService.getDeepCategory(categoryId).getData();
        }
        if(StringUtils.isNotBlank(keyword)){
            keyword = new StringBuilder().append("%").append(keyword).append("%").toString();
        }

        //排序处理
        PageHelper.startPage(pageNum, pageSize);
        if(StringUtils.isNotBlank(orderBy)) {
            if(Const.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)) {
                String[] orderByArray = orderBy.split("_");
                PageHelper.orderBy(orderByArray[0]+" "+orderByArray[1]);
            }
        }
        List<Product> productList = productMapper.selectByNameAndProductIds(StringUtils.isBlank(keyword) ? null : keyword,
                categoryIdList.size()==0 ? null : categoryIdList);
        List<ProductListVo> productListVoList = Lists.newArrayList();
        for(Product productItem : productList){
            ProductListVo productListVo = assembleProductListVo(productItem);
            productListVoList.add(productListVo);
        }
        //通过数据库查询到POJO用于分页，VO展示需要的查询结果
        PageInfo pageResult = new PageInfo(productList);
        pageResult.setList(productListVoList);
        return ServerResponse.createBySuccess(pageResult);
    }

    /**
     * 2.产品详情
     * */
    @Override
    public ServerResponse<ProductDetailVo> getProductDetail(Integer productId) {
        if(productId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product == null){
            return ServerResponse.createByErrorMessage("产品已下架或删除");
        }
        if(product.getStatus() != Const.ProductStatusEnum.ON_SALE.getCode()){
            return ServerResponse.createByErrorMessage("产品已下架或删除");
        }
        ProductDetailVo productDetailVo = assembleProductDetailVo(product);
        return ServerResponse.createBySuccess(productDetailVo);
    }
}
