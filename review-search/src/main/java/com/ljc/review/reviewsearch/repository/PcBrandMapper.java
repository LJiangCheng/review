package com.ljc.review.reviewsearch.repository;


import com.toolmall.business.product.back.BrandVO;
import com.toolmall.business.product.back.SimpleBrandVO;
import com.toolmall.business.product.back.SimpleBrandVOExtCategory;
import com.toolmall.business.product.bean.PcBrand;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
***************************************************************
* 公司名称    :杭州巨星科技股份有限公司
* 系统名称    :土猫网电商平台-Mybatis+mysql 最新架构版 v7.20
* 类 名 称    :
* 功能描述    :
* 业务描述    :
* 作 者 名    :@Author Royal(方圆)
* 开发日期    :2018-2-26 16:32:13
* Created     :IntelliJ IDEA
***************************************************************
* 修改日期    :
* 修 改 者    :
* 修改内容    :
***************************************************************
*/
@Repository
public interface PcBrandMapper extends IGenericDao<PcBrand,Integer> {
	
    //==============以下为mybatis使用demo---此方法带注解Delete_sql实现=======================
    //@Delete("delete from pc_brand where id =#{id}")
    //int delete(java.lang.Integer id);
	
    //==============此方法带注解Update_sql实现=======================
	//@Update("update pc_brand set status=#{status} where AutoId=#{id}")
    //int updateUserState(@Param("id")int  autoId, @Param("status")int status);
	
	//==============此方法带注解Select_sql实现=======================
	//@Select("select * from pc_brand where UserId=#{phoneId} and AutoId=#{id} order by AutoId desc limit 0,1")
    //Userinfo  getSingleUserById(@Param("id")int pbAutoId,@Param("phoneId")Long phoneId);
	
	//==============此方法对应 xml里面========================
	//Userinfo selectById(Integer id);
	
	/**
     * 根据ID列表查询
     * @param list
     * @return
     */
    List<PcBrand> selectVOListByIds(List<Long> list);
    
    /**
     * 根据ID查询品牌
     * @param id
     * @return
     */
    BrandVO selectVOById(Long id);
    
    /**
     * 根据ID查询品牌
     * @param id
     * @return
     */
    SimpleBrandVO selectSimpleVOById(Long id);
    
    /**
     * 根据商品类目ID和商品等级查询简单品牌列表
     * @param categoryId
     * @param grade
     * @return
     */
    List<SimpleBrandVO> selectSimpleBrandListByGrade(@Param("categoryId") Long categoryId, @Param("grade") Integer grade);

    List<BrandVO> selectVOList();

    Long findByIgoreCaseName(String name);

    List<SimpleBrandVO> selectSimpleVOList();
    
    /**
     * 根据商品分类ID列表查询简单品牌列表
     * @param list
     * @return
     */
    List<SimpleBrandVOExtCategory> selectByCategoryList(@Param("list") List<Long> list);

    void batchUpdateSeo(@Param("list") List<PcBrand> list);
}

