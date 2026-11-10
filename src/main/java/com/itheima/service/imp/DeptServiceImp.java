package com.itheima.service.imp;

import com.itheima.aoptest.MyLog;
import com.itheima.mapper.DeptMapper;
import com.itheima.pojo.Dept;
import com.itheima.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(rollbackFor = Exception.class)//为某一类设定事务
@Service
public  class DeptServiceImp implements DeptService {

    @Autowired
    DeptMapper deptMapper;

    @Override
    public List<Dept> list() {

        List<Dept> deptList=deptMapper.list();

        return deptList;

    }

    @Override
    public boolean deleteDeptByid(Integer id) {
        if(deptMapper.deleteDeptByid(id)){

            deptMapper.deleteEmpByid(id);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void addDept(Dept dept) {
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.addDept(dept);
    }

    @Override
    @MyLog
    public Dept selectDeptByid(Integer id) {
        Dept dept= deptMapper.selectDeptByid(id);
        return dept;
    }

    @Override
    public void updateDept(Dept dept) {

        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.updateDept(dept);
    }

}

/*
事务的传播行为：(属性)（propagation）
默认值：required需要事务如果有就加入老事务，如果没有仅创建新事务
（老事务出问题了新事物跟着一起回滚
）
required——new:需要新事物，无论是否有都直接创建新事务（新事物不管老事务）

 */
/*
propagation=propagation.xxxxx
 */