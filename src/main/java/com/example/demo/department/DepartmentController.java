package com.example.demo.department;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.projection.ProjectionFactory;

import com.example.demo.GenericController;
import com.example.demo.model.Department;

@RestController
@RequestMapping("/department")
public class DepartmentController extends GenericController<Department, Integer> {

	
	//Calling generic controller by passing department repository
	
	@Autowired
	public DepartmentController(DeptRepo repo) {
		super(repo);

	}
}
	

