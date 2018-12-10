package com.ndg.springdemo.department;

import java.net.URISyntaxException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ndg.springdemo.common.ApplicationRepository;
import com.ndg.springdemo.common.GenericController;
import com.ndg.springdemo.model.Department;


@RestController
@RequestMapping(DepartmentController.URI)
public class DepartmentController extends GenericController<Department, Integer> {

	@Autowired
	private DeptRepo repository;
	@Autowired
	private ProjectionFactory factory;

	static final String URI = "/department";

	// Calling generic controller by passing department repository
	@Autowired
	public DepartmentController(ApplicationRepository<Department, Integer> repo) {
		super(URI, repo);
	}

	@RequestMapping
	public ResponseEntity<Page<DepartmentRecord>> all(Pageable pageable,
			@RequestParam(value = "search", required = false) String search) {

		Page<Department> x = super.allImpl(pageable, search);

		Page<DepartmentRecord> projected = x.map(l -> factory.createProjection(DepartmentRecord.class, l));

		return super.listResponse(projected, pageable, search);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	protected ResponseEntity<Department> get(@PathVariable Integer id) {
		return super.respondToGet(id, super.getImpl(id));
	}

	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	protected ResponseEntity<Department> create(@RequestBody Department department) throws URISyntaxException {

		Department result = super.createImpl(department);
		return super.created(result.getDeptid(), result);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Department update(@PathVariable int id, @RequestBody Department json) {

		Department entity = repository.findBydeptid(id);

		BeanUtils.copyProperties(entity, json);

		Department result = super.createImpl(entity);

		return result;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	protected ResponseEntity<Department> delete(@PathVariable Integer id) {
		super.deleteImpl(id);
		return super.noContent(id);

	}
}
