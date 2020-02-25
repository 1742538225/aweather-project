package com.id0304.mybatis;

import org.apache.ibatis.annotations.*;

public interface BaseDao {
    //自定义sql语句,插入
    @InsertProvider(type = BaseProvider.class, method = "save")
    void save(@Param("obj") Object obj, @Param("table") String table);

    //自定义sql语句,修改
    @UpdateProvider(type = BaseProvider.class, method = "update")
    void update(@Param("obj") Object obj, @Param("table") String table);
}