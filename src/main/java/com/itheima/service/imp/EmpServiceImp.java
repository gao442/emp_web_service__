package com.itheima.service.imp;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.EmpMapper;
import com.itheima.pojo.Emp;
import com.itheima.pojo.PageBean;
import com.itheima.pojo.SelectEmp;
import com.itheima.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Transactional(rollbackFor = Exception.class)
/*
只有运行时异常才会对事务进行回滚，如果是其他异常不会回滚，事务会提交
 */
@Service
public  class EmpServiceImp implements EmpService {

    @Autowired
    private EmpMapper empMapper;

    @Override
    public PageBean selectEmpLimit(Integer page, Integer pageSize){

//        Integer start=(page-1)*pageSize;
//        List<Emp> empList = empMapper.selectEmpPage(start, pageSize);
//
//        Long total = empMapper.selectEmpSum();
//        return new PageBean(empList, total);

        if(page == null) page = 1;
        if(pageSize == null) pageSize = 10;
        PageHelper.startPage(page,pageSize);
        //普通获取全部，封装到列表里
        List<Emp> empList = empMapper.list();
        //把得到的List强转成Page
        Page<Emp> p=(Page<Emp>)empList;
        //从Page里面获取数据封装到PageBean
        PageBean pageBean=new PageBean(p.getResult(), p.getTotal());
        //返回Page Bean
        return pageBean;

    }

    @Override
    public PageBean selectEmpBy(SelectEmp emp) {
        if(emp.getPage()==null) emp.setPage(1);
        if(emp.getPageSize()==null) emp.setPageSize(5);

        PageHelper.startPage(emp.getPage(),emp.getPageSize());
        List<Emp> empList= empMapper.listBy(emp);
        Page<Emp> page=(Page<Emp>) empList;
        PageBean pageBean=new PageBean(page.getResult(),page.getTotal());
        return pageBean;
    }

    @Override
    public void deleteEmps(Integer[] ids) {
        empMapper.deleteEmps(ids);
    }

    @Override
    public void insertEmp(Emp emp) {

        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.insertEmp(emp);
    }

    @Override
    public Emp selectEmpByid(Integer id) {
        Emp emp= empMapper.selectEmpByid(id);
        return emp;
    }

    @Override
    public void updateEmp(Emp emp) {
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.updateEmp(emp);
    }

    @Override
    public Emp login(Emp emp) {
        Emp e= empMapper.login(emp);
        return e;
    }


}
