package com.ljc.review.reviewsearch.repository;

import com.toolmall.business.product.bean.PcProductCategoryBack;
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
* 开发日期    :2018-7-5 10:37:13
* Created     :IntelliJ IDEA
***************************************************************
* 修改日期    :
* 修 改 者    :
* 修改内容    :
***************************************************************
*/
@Repository
public interface PcProductCategoryBackMapper extends IGenericDao<PcProductCategoryBack,Integer> {
	
    //==============以下为mybatis使用demo---此方法带注解Delete_sql实现=======================
    //@Delete("delete from pc_product_category_back where id =#{id}")
    //int delete(java.lang.Integer id);
	
    //==============此方法带注解Update_sql实现=======================
	//@Update("update pc_product_category_back set status=#{status} where AutoId=#{id}")
    //int updateUserState(@Param("id")int  autoId, @Param("status")int status);
	
	//==============此方法带注解Select_sql实现=======================
	//@Select("select * from pc_product_category_back where UserId=#{phoneId} and AutoId=#{id} order by AutoId desc limit 0,1")
    //Userinfo  getSingleUserById(@Param("id")int pbAutoId,@Param("phoneId")Long phoneId);
	
	//==============此方法对应 xml里面========================
	//Userinfo selectById(Integer id);
	
	List<PcProductCategoryBack> getPcProductCategoryBackList(PcProductCategoryBack pcProductCategory);

    List<PcProductCategoryBack> getLeafCategoryList();

}

