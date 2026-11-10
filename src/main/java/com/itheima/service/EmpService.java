package com.itheima.service;

import com.itheima.pojo.Emp;
import com.itheima.pojo.PageBean;
import com.itheima.pojo.SelectEmp;

public interface EmpService {
    PageBean selectEmpLimit(Integer page, Integer pageSize);

    PageBean selectEmpBy(SelectEmp emp);

    void deleteEmps(Integer[] ids);

    void insertEmp(Emp emp);

    Emp selectEmpByid(Integer id);

    void updateEmp(Emp emp);

    Emp login(Emp emp);
}