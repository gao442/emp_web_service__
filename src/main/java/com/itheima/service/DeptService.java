package com.itheima.service;

import com.itheima.pojo.Dept;

import java.util.List;

public interface DeptService {
    List<Dept> list();

    boolean deleteDeptByid(Integer id);

    void addDept(Dept dept);

    Dept selectDeptByid(Integer id);

    void updateDept(Dept dept);
}
