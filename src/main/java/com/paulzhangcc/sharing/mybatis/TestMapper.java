package com.paulzhangcc.sharing.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author paul
 * @description
 * @date 2017/12/29
 */
@Mapper
public interface TestMapper {
    @Select({"select 1"})
    Integer test();
}
