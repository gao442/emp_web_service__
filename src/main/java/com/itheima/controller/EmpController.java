package com.itheima.controller;

import com.itheima.pojo.Emp;
import com.itheima.pojo.PageBean;
import com.itheima.pojo.Result;
import com.itheima.pojo.SelectEmp;
import com.itheima.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/emps")
@RestController
public class EmpController {

    @Autowired
    private EmpService empService;

    @GetMapping
    public Result selectEmpLimit(Integer page,Integer pageSize){

        PageBean pageBean= empService.selectEmpLimit(page,pageSize);
        return Result.success(pageBean);
    }

    @PostMapping("/limit")
    public Result selectEmpBy(@RequestBody SelectEmp emp){
        PageBean pageBean= empService.selectEmpBy(emp);
        return Result.success(pageBean);
    }

    @DeleteMapping("/{ids}")
    public Result deleteEmps(@PathVariable Integer[] ids){

        empService.deleteEmps(ids);
        return Result.success();
    }

    @PostMapping
    public Result addEmp(@RequestBody Emp emp){

        log.info("新增员工");
        empService.insertEmp(emp);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result selectEmpByid(@PathVariable Integer id){
        Emp emp= empService.selectEmpByid(id);
        return Result.success(emp);
    }

    @PutMapping
    public Result updateEmp(@RequestBody Emp emp){
        empService.updateEmp(emp);
        return Result.success();
    }

}
