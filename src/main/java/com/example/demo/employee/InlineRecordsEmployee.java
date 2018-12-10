package com.example.demo.employee;

import java.util.Date;

import org.springframework.data.rest.core.config.Projection;

import com.example.demo.department.InlineRecordsDepartment;
import com.example.demo.model.Employee;

@Projection(name = "inlineRecordsemployee", types = { Employee.class }) //FOR EMPLOYEE
public interface InlineRecordsEmployee{
	int getid();
	String getname();

	InlineRecordsDepartment getdeptid();
	//
	
	String getSkill();
	float getSalary();
	int getGrade();
	String getCity();
	String getCountry();
	Date getDoj();
	String getdesg();
}
