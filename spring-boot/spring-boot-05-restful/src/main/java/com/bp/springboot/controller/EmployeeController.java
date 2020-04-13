package com.bp.springboot.controller;

import com.bp.springboot.dao.DepartmentDao;
import com.bp.springboot.dao.EmployeeDao;
import com.bp.springboot.entities.Department;
import com.bp.springboot.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.List;

/**
 * @Auther: daiyu
 * @Date: 13/4/20 10:37
 * @Description:
 */
@Controller
public class EmployeeController {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private DepartmentDao departmentDao;

    @GetMapping("/emps")
    public String list(Model model) {

        Collection<Employee> employeeList = employeeDao.getAll();
        model.addAttribute("emps", employeeList);
        return "emp/list";
    }

    @GetMapping("/emp")
    public String toAddPage(Model model) {
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("depts", departments);
        return "emp/add";
    }
}
