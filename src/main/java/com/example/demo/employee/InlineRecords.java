package com.example.demo.employee;

import java.util.Date;

import org.springframework.data.rest.core.config.Projection;

import com.example.demo.model.Employee;

@Projection(name = "inlineRecords", types = { Employee.class }) //FOR EMPLOYEE
public interface InlineRecords {
	int getid();
	String getname();
	String getSkill();
	float getSalary();
	int getGrade();
	String getCity();
	String getCountry();
	Date getDoj();
	String getDesg();
}
