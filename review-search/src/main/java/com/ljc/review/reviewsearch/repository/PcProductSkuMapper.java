package com.ljc.review.reviewsearch.repository;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.toolmall.business.product.back.ProductParameterVO;
import com.toolmall.business.product.back.SimpleProductVO;
import com.toolmall.business.product.bean.PcProductSku;
import com.toolmall.business.product.vo.AllProductVO;
import com.toolmall.business.product.vo.ProductExportVO;
import com.toolmall.business.product.vo.PushErpVO;
import com.toolmall.business.product.vo.SkuSalesVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
***************************************************************
* 公司名称    :杭州巨星科技股份有限公司
* 系统名称    :土猫网电商平台-Mybatis+mysql 最新架构版 v7.20
* 类 名 称    :
* 功能描述    :
* 业务描述    :
* 作 者 名    :@Author Royal(方圆)
* 开发日期    :2018-2-26 16:32:22
* Created     :IntelliJ IDEA
***************************************************************
* 修改日期    :
* 修 改 者    :
* 修改内容    :
***************************************************************
*/
@Repository
@MyBatisRepository
public interface PcProductSkuMapper extends IGenericDao<PcProductSku,Integer> {
	
    //==============以下为mybatis使用demo---此方法带注解Delete_sql实现=======================
    //@Delete("delete from pc_product_sku where id =#{id}")
    //int delete(java.lang.Integer id);
	
    //==============此方法带注解Update_sql实现=======================
	//@Update("update pc_product_sku set status=#{status} where AutoId=#{id}")
    //int updateUserState(@Param("id")int  autoId, @Param("status")int status);
	
	//==============此方法带注解Select_sql实现=======================
	//@Select("select * from pc_product_sku where UserId=#{phoneId} and AutoId=#{id} order by AutoId desc limit 0,1")
    //Userinfo  getSingleUserById(@Param("id")int pbAutoId,@Param("phoneId")Long phoneId);
	
	//==============此方法对应 xml里面========================
	//Userinfo selectById(Integer id);
	
	PcProductSku getBySkuId(Integer skuId);
	
	List<AllProductVO> getAllProductList(@Param("allProductVO") AllProductVO allProductVO, PageBounds pageBounds);
	
	public long getAllProductCount(@Param("allProductVO") AllProductVO allProductVO);

    List<PcProductSku> getBySpuId(@Param("spuId") Integer spuId);

    List<SimpleProductVO> selectCategoryProductByIdList(Map<String, Object> query);

    List<SimpleProductVO> selectTodayListByIds(Map<String, Object> map);

    SimpleProductVO getProductVOById(Integer id);

    PcProductSku getByUnicode(@Param("unicode") String unicode);
    
    Long getProductExportCount(Map<String, Object> map);
    
    List<ProductExportVO> getProductExportList(Map<String, Object> map, PageBounds pageBounds);
    
    /**
     * 获取商品参数
     * @param map
     * @return
     */
    List<ProductParameterVO> getParamList(Map<String, Object> map);

    PcProductSku getWithPropertyBySkuId(Integer skuid);

    void batchUpdateSales(@Param("list") List<SkuSalesVO> paramList);

    List<SkuSalesVO> exchangeUnicodeToId(@Param("list") List<SkuSalesVO> paramList);

    List<String> getAllUnicode();

    void batchUpdateErpPrice(@Param("list") List<PcProductSku> skuList);

    List<String> getAllUnicodeShelved();

    List<PcProductSku> batchGetWithPropertyBySkuids(@Param("list") List<Integer> skuIdList);

    List<PcProductSku> batchGetBySkuids(@Param("list") List<Integer> skuIdList);
    
    List<PushErpVO> getPushErpList();
    
    void updatePushErp(@Param("list") List<Integer> skuIds);
    
    List<PcProductSku> getBusinessProductList();
    
    void updateSyncStock(PcProductSku pcProductSku);
    
    void batchUpdateSyncStock(@Param("list") List<PcProductSku> skuList);

}




























