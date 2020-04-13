package com.bp.springboot.controller;

import com.bp.springboot.dao.DepartmentDao;
import com.bp.springboot.dao.EmployeeDao;
import com.bp.springboot.entities.Department;
import com.bp.springboot.entities.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * @Auther: daiyu
 * @Date: 13/4/20 10:37
 * @Description:
 */
@Controller
@Slf4j
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

    @PostMapping("/emp")
    public String addEmp(Employee employee) {

        log.info("employee: ", employee.toString());
        employeeDao.save(employee);
        return "redirect:/emps";
    }

    @GetMapping("/emp/{id}")
    public String toEditEmp(@PathVariable("id") Integer id, Model model) {

        Employee employee = employeeDao.get(id);
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("emp", employee);
        model.addAttribute("depts", departments);
        return "emp/add";
    }

    @PutMapping("/emp")
    public String updateEmployee(Employee employee) {
        log.info("Employee", employee);
        employeeDao.save(employee);
        return "redirect:/emps";
    }


    @DeleteMapping("/emp/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id){
        employeeDao.delete(id);
        return "redirect:/emps";
    }
}
