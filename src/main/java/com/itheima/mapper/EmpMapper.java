package com.itheima.mapper;

import com.itheima.pojo.Emp;
import com.itheima.pojo.SelectEmp;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EmpMapper {

//    @Select("select * from emp limit #{start},#{pageSize}")
//    List<Emp> selectEmpPage(@Param("start") Integer start, @Param("pageSize") Integer pageSize);
//
//    @Select("select count(*) from emp")
//    Long selectEmpSum();


    @Select("select * from emp")
    List<Emp> list();


    List<Emp> listBy(SelectEmp emp);


    void deleteEmps(@Param("ids") Integer[] ids);


    @Insert("insert into emp (username, name, gender, image, job, entrydate, dept_id, create_time, update_time) " +
            "VALUES (#{username},#{name},#{gender},#{image},#{job},#{entrydate},#{deptId},#{createTime},#{updateTime})")
    void insertEmp(Emp emp);

    @Select("select * from emp where id=#{id}")
    Emp selectEmpByid(Integer id);


    void updateEmp(Emp emp);

    @Select("select * from emp where username=#{username} and password=#{password}")
    Emp login(Emp emp);
}
