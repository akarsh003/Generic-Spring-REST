package com.example.demo.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;

import com.example.demo.GenericController;
import com.example.demo.model.Employee;


@RestController
@RequestMapping("/employee")
public class EmployeeController extends GenericController<Employee,Integer>{

//	Calling generic controller by passing employee repository
	
	@Autowired
	public EmployeeController(EmplRepo repo) {
		super(repo);
	}

	
}
