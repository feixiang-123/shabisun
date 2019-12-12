package com.shsxt.base;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.vo.CusDevPlan;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseService<T, ID> {

    @Autowired
    private BaseMapper<T, ID> baseMapper;

    public int save(T entity){
        return baseMapper.save(entity);
    }

    public ID saveHasKey(T entity)  {
        try {
            Method method= entity.getClass().getDeclaredMethod("getId",null);
            return (ID) method.invoke(entity,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public  int saveBatch(List<T> entities){
        return baseMapper.saveBatch(entities);
    }


    public T queryById(ID id){
        return baseMapper.queryById(id);
    }

    public List<T> queryByParams(BaseQuery baseQuery){
        return baseMapper.queryByParams(baseQuery);
    }

    public  int update(T entity){
        return baseMapper.update(entity);
    }

    public  int updateBatch(Map<String,Object> map){
        return baseMapper.updateBatch(map);
    }

    public int delete(ID id){
        return baseMapper.delete(id);
    }

    public  int deleteBatch(ID[] ids){
        return baseMapper.deleteBatch(ids);
    }


    /**
     * 分页查询
     * @param baseQuery
     * @return
     */
    public PageInfo<T> queryForPage(BaseQuery baseQuery){
        PageHelper.startPage(baseQuery.getPageNum(), baseQuery.getPageSize());
        return new PageInfo<T>(queryByParams(baseQuery));
    }



    public Map<String,Object> queryByParamsForDataGrid(BaseQuery baseQuery){
        PageInfo<T> pageInfo= queryForPage(baseQuery);
        Map<String,Object> result= new HashMap<String,Object>();
        result.put("total",pageInfo.getTotal());
        result.put("rows",pageInfo.getList());
        return result;
    }


}
