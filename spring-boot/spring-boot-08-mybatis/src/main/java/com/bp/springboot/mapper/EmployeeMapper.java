package com.bp.springboot.mapper;

import com.bp.springboot.bean.Employee;

public interface EmployeeMapper {

    public Employee getEmpById(Integer id);

    public void insertEmp(Employee employee);
}
