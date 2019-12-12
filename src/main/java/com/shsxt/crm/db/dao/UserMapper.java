package com.shsxt.crm.db.dao;

import com.shsxt.base.BaseMapper;
import com.shsxt.crm.vo.User;

public interface UserMapper extends BaseMapper<User,Integer> {

    User queryUserByUserName(String userName);
}