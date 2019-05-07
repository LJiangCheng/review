package com.ljc.review.reviewsearch.repository;


import com.toolmall.business.product.back.CategoryVO;
import com.toolmall.business.product.back.ProductCategory;
import com.toolmall.business.product.back.ProductCategoryVO;
import com.toolmall.business.product.back.SimpleCategoryVO;
import com.toolmall.business.product.bean.PcProductCategory;
import com.toolmall.business.product.bean.PcProductContent;
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
public interface PcProductCategoryMapper extends IGenericDao<PcProductCategory,Integer> {
	
    //==============以下为mybatis使用demo---此方法带注解Delete_sql实现=======================
    //@Delete("delete from pc_product_category where id =#{id}")
    //int delete(java.lang.Integer id);
	
    //==============此方法带注解Update_sql实现=======================
	//@Update("update pc_product_category set status=#{status} where AutoId=#{id}")
    //int updateUserState(@Param("id")int  autoId, @Param("status")int status);
	
	//==============此方法带注解Select_sql实现=======================
	//@Select("select * from pc_product_category where UserId=#{phoneId} and AutoId=#{id} order by AutoId desc limit 0,1")
    //Userinfo  getSingleUserById(@Param("id")int pbAutoId,@Param("phoneId")Long phoneId);
	
	//==============此方法对应 xml里面========================
	//Userinfo selectById(Integer id);
	
	/**
     * 根据类目ID列表查询类目列表
     * @param list
     * @return
     */
    List<PcProductCategory> selectByIdList(List<Long> list);
    
    /**
     * 根据商品类目的一级类目id列表、二级类目列表、三级类目列表
     * @param firstList
     * @param secondList
     * @param thirdList
     * @return
     */
    List<Long> getThirdByGroupIdList(@Param("firstList") List<Integer> firstList, @Param("secondList") List<Integer> secondList, @Param("thirdList") List<Integer> thirdList);

    /**
     * 根据ID查询商品类目
     * @param id
     * @return
     */
    CategoryVO selectVOById(Long id);

    /**
     * 根据三级类目查询一级类目
     * @param id
     * @return
     */
    CategoryVO selectParentVOById(Long id);

    /**
     * 查询一级类目ID查询三级商品类目列表
     * @return
     */
    List<CategoryVO> getLv3ById(Long categoryId);

    /**
     * 根据级别查询类目
     * @param
     * @return
     */
    List<CategoryVO> getAllListByGrade(Integer grade);

    /**
     * 查询商品列表
     * @return
     */
    List<ProductCategoryVO> selectVOList();

    /**
     * 根据名称查询商品类目
     * @param name
     * @return
     */
    List<SimpleCategoryVO> getQueryList(String name);

    List<PcProductContent> getCategoryContentList(PcProductCategory pcProductCategory);

    List<Integer> getBackCategoryIds(Integer frontCategoryId);

    /**
     * 根据ID列表获取相关列表
     * @param list
     * @return
     */
    List<SimpleCategoryVO> getSimpleCategoryVOList(List<Long> list);

    /**
     * 根据ID列表查询商品类目列表
     * @param id
     * @return
     */
    List<ProductCategory> getChildrenById(Long id);

    /**
     * 根据ID列表查询商品类目列表
     * @param list
     * @return
     */
    List<ProductCategory> getChildrenByIdList(List<Long> list);

    /**
     * 根据商品类目ID查询所有的三级类目ID
     * @param map
     * @return
     */
    List<Long> getAllLv3Ids(Map<String, Object> map);

    List<ProductCategoryVO> getListByGrade(@Param("grade") Integer grade);

    /**
     * 获取所有可用的类目
     * @return
     */
    List<ProductCategory> getLv3List();

    /**
     * 根据品牌ID查询商品分类列表
     * @param brandId
     * @return
     */
    List<ProductCategoryVO> getByBrandId(Long brandId);

    /**
     * 根据商品分类id查询父类的商品分类
     * @param list
     * @return
     */
    List<ProductCategoryVO> selectVOByIdList(@Param("list") List<Long> list);
    
    /**
     * 根据品牌ID查询商品二级分类列表
     * @return
     */
    List<SimpleCategoryVO> getLv2ByBrandId(Long brandId);

    /**
     * 根据品牌ID查询商品三级分类列表
     * @return
     */
    List<SimpleCategoryVO> getLv3ByBrandId(Long brandId);
    
    /**
	 * 查询所有分类ID
	 * @return
	 */
	List<Integer> getAllCategoryIds();

    List<Integer> getBackCategoryIdsByFrontIds(List<Long> frontCategoryIds);

    void batchUpdateSeo(@Param("list") List<PcProductCategory> list);
    
    List<ProductCategoryVO> getSiblingList(@Param("grade") Integer grade, @Param("parentId") Integer parentId, @Param("categoryId") Integer categoryId);
}

