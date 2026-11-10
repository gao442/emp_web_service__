package com.itheima.mapper;

import com.itheima.pojo.Dept;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface DeptMapper {

    @Select("select * from dept")
    List<Dept> list();

    @Delete("delete from dept where id=#{id}")
    boolean deleteDeptByid(Integer id);


    @Insert("insert into dept (name,create_time,update_time) values (#{name},#{createTime},#{updateTime})")
    void addDept(Dept dept);

    @Select("select * from dept where id=#{id}")
    Dept selectDeptByid(Integer id);


    @Update("update dept set name=#{name},update_time=#{updateTime} where id=#{id}")
    void updateDept(Dept dept);

    @Delete("delete from emp where dept_id=#{id}")
    void deleteEmpByid(Integer id);
}
