package com.example.demo.employee;

import com.example.demo.department.DeptRepo;
import com.example.demo.department.DepartmentRecord;
import com.example.demo.model.Department;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URISyntaxException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.GenericController;
import com.example.demo.model.Employee;

import java.net.URISyntaxException;
import java.util.Optional;

@RequestMapping(EmployeeController.URI)
@RestController
public class EmployeeController extends GenericController<Employee, Integer> {

	static final String URI = "/employee";

	@Autowired
	public EmployeeController(EmployeeRepo repo) {
		super(URI, repo);
	}

	@Autowired
	private ProjectionFactory factory;

	@RequestMapping
	public ResponseEntity<Page<EmployeeRecord>> all(Pageable pageable,
			@RequestParam(value = "search", required = false) String search) {

		Page<Employee> x = super.allImpl(pageable, search);
		Page<EmployeeRecord> projected = x.map(l -> factory.createProjection(EmployeeRecord.class, l));
		return super.listResponse(projected, pageable, search);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	protected ResponseEntity<Employee> get(@PathVariable Integer id) {
		return super.respondToGet(id, super.getImpl(id));
	}

	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	protected ResponseEntity<Employee> create(@RequestBody Employee employee) throws URISyntaxException {

		if (employee.getId() != 0) {
			return super.cannotCreateEntityWithKey();
		}
		Employee result = super.createImpl(employee);
		return super.created(result.getId(), result);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = { MediaType.APPLICATION_JSON_VALUE })
	protected ResponseEntity<Employee> update(@PathVariable Integer id, @RequestBody Employee employee) {
		return super.getImpl(id).map(oldEmployee -> {
			Employee updatedEntity = super.updateImpl(oldEmployee, employee);
			return super.updated(id, updatedEntity);
		}).orElse(super.updateNotFound(id));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	protected ResponseEntity<Employee> delete(@PathVariable Integer id) {
		super.deleteImpl(id);
		return super.noContent(id);
	}
}
