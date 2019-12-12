package com.shsxt.base;

import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

public interface BaseMapper<T,ID> {
    /**
     * 添加记录返回影响行数
     */
    public int save(T entity) throws DataAccessException;

    /**
     * 添加记录返回主键
     */
    public  ID  saveHasKey(T entity) throws DataAccessException;

    /**
     * 批量添加记录
     */
    public  int  saveBatch(List<T> entities) throws DataAccessException;


    /**
     * 详情查询
     * @param id
     * @return
     */
    public T queryById(ID id) throws DataAccessException;


    /**
     * 多条件列表查询
     * @param baseQuery
     * @return
     */
    public List<T> queryByParams(BaseQuery baseQuery) throws DataAccessException;

    /**
     * 更新单条记录
     * @param entity
     * @return
     */
    public int update(T entity) throws DataAccessException;


    /**
     * 批量更新
     * @param map
     * @return
     */
    public int updateBatch(Map<String, Object> map) throws DataAccessException;

    /**
     * 删除单条记录
     * @param id
     * @return
     */
    public int delete(ID id) throws DataAccessException;


    /**
     * 批量删除
     * @param ids
     * @return
     */
    public int deleteBatch(ID[] ids) throws DataAccessException;












}
