package com.ljc.review.reviewsearch.repository;

import com.toolmall.business.product.back.*;
import com.toolmall.business.product.bean.PcProperty;
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
* 开发日期    :2018-2-26 16:32:31
* Created     :IntelliJ IDEA
***************************************************************
* 修改日期    :
* 修 改 者    :
* 修改内容    :
***************************************************************
*/
@Repository
@MyBatisRepository
public interface PcPropertyMapper extends IGenericDao<PcProperty,Integer> {
	
    //==============以下为mybatis使用demo---此方法带注解Delete_sql实现=======================
    //@Delete("delete from pc_property where id =#{id}")
    //int delete(java.lang.Integer id);
	
    //==============此方法带注解Update_sql实现=======================
	//@Update("update pc_property set status=#{status} where AutoId=#{id}")
    //int updateUserState(@Param("id")int  autoId, @Param("status")int status);
	
	//==============此方法带注解Select_sql实现=======================
	//@Select("select * from pc_property where UserId=#{phoneId} and AutoId=#{id} order by AutoId desc limit 0,1")
    //Userinfo  getSingleUserById(@Param("id")int pbAutoId,@Param("phoneId")Long phoneId);
	
	//==============此方法对应 xml里面========================
	//Userinfo selectById(Integer id);



	/**
     * 根据商品类目ID列表查询
     * @param map
     * @return
     */
    List<Attribute> selectAttributeList(Map<String, Object> map);
    
    /**
     * 根据属性ID列表，产品ID列表查询属性值
     * @return
     */
    List<AttributeValue> selectAttrValueList(Map<String, Object> map);
    
    /**
     * 查询ID列表
     * @param map
     * @return
     */
    List<Long> selectProductIdList(Map<String, Object> map);
    
    
    /**
     * 获取商品属性
     * @param map
     * @return
     */
    List<ProductAttributeVO> getAttrList(Map<String, Object> map);
    
    /**
     * 获取商品参数
     * @param map
     * @return
     */
    List<ProductParameterVO> getParamList(Map<String, Object> map);
    
    /**
     * 获取商品规格
     * @param map
     * @return
     */
    List<ProductSpecificationVO> getSpecList(Map<String, Object> map);
    
    /**
     * 获取商品规格
     * @param map
     * @return
     */
    List<Map<String, Object>> getSpecListNew(Map<String, Object> map);

}






































