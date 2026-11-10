package com.itheima.controller;

import com.itheima.annotation.Log;
import com.itheima.pojo.Dept;
import com.itheima.pojo.Result;
import com.itheima.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/depts")
public class DeptController {

    @Autowired
    DeptService deptService;

    //private static Logger log= LoggerFactory.getLogger(DeptController.class);

    //@RequestMapping(value = "/depts",method = RequestMethod.GET)
    @GetMapping
    @Log
    public Result list(){

        log.info("查询全部部门数据");
        List<Dept> deptList=deptService.list();
        return Result.success(deptList);
    }

    @DeleteMapping("/{id}")
    public Result deleteDeptByid(@PathVariable Integer id){
        log.info("根据id删除部门,{}",id);
        if(deptService.deleteDeptByid(id)){
            return Result.success();
        }else{
            return Result.error("删除失败,找不到这个部门 ");
        }

    }

    @PostMapping
    public Result addDept(@RequestBody Dept dept){
        deptService.addDept(dept);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result selectDeptByid(@PathVariable Integer id){
        Dept dept= deptService.selectDeptByid(id);
        return Result.success(dept);
    }

    @PutMapping
    public Result updateDept(@RequestBody Dept dept){

        deptService.updateDept(dept);
        return Result.success();
    }



}
