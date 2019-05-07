
package com.ljc.review.search.repository;

import java.util.List;
public interface IGenericDao<T,PK> {

    List<T> search(T params);

    List<T> selectPageList(T model);

    long selectPageCount(T model);

    int insert(T model);

    int insertByBatch(List models);

    int update(T model);

    int updateList(List models);

    T selectById(PK id);

    List<T> selectList(T model);

    T selectOne(T model);

    int delete(PK id);

    int deleteByIds(String[] ids);

}
