package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectEmp {
    private String name;
    private Integer gender;
    private LocalDate entrydate;
    private Integer page;
    private Integer pageSize;
}
