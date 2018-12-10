package com.ndg.springdemo.department;

import org.springframework.data.rest.core.config.Projection;

import com.ndg.springdemo.model.Department;
import com.ndg.springdemo.model.Employee;

@Projection(name = "departmentRecord", types = { Department.class })// THIS IS FOR DEPARTMENT
public interface DepartmentRecord {
	int getDeptid();
	String getdeptname();
//	Employee getid();
	InlineEmployeeRecord getdepthead();
	
}
