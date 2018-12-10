package com.example.demo.employee;

import java.util.Date;

import org.springframework.data.rest.core.config.Projection;

import com.example.demo.department.DepartmentRecord;
import com.example.demo.model.Employee;

@Projection(name = "employeeRecord", types = { Employee.class }) //FOR EMPLOYEE
public interface EmployeeRecord{
	int getid();
	String getname();

	DepartmentRecord getdeptid();
	//
	
	String getSkill();
	float getSalary();
	int getGrade();
	String getCity();
	String getCountry();
	Date getDoj();
	String getdesg();
}
