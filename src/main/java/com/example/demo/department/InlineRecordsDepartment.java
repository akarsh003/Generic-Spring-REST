package com.example.demo.department;

import org.springframework.data.rest.core.config.Projection;

import com.example.demo.employee.InlineRecords;
import com.example.demo.model.Department;
import com.example.demo.model.Employee;

@Projection(name = "inlineRecordsdepartment", types = { Department.class })// THIS IS FOR DEPARTMENT
public interface InlineRecordsDepartment {
	int getDeptid();
	String getdeptname();
//	Employee getid();
	InlineRecords getid();
	
}
