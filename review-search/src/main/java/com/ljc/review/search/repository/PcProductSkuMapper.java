package com.ljc.review.search.repository;

import com.ljc.review.search.entity.PcProductSku;
import com.ljc.review.search.entity.vo.SkuToElasticSearchVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PcProductSkuMapper extends IGenericDao<PcProductSku, Integer> {
    List<SkuToElasticSearchVO> getAllProduct();

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
}

