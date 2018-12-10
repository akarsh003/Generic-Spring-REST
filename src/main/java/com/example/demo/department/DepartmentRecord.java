package com.example.demo.department;

import org.springframework.data.rest.core.config.Projection;

import com.example.demo.employee.InlineEmployeeRecord;
import com.example.demo.model.Department;
import com.example.demo.model.Employee;

@Projection(name = "departmentRecord", types = { Department.class })// THIS IS FOR DEPARTMENT
public interface DepartmentRecord {
	int getDeptid();
	String getdeptname();
//	Employee getid();
	InlineEmployeeRecord getdepthead();
	
}
