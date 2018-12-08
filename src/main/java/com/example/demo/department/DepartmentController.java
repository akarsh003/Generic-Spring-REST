package com.example.demo.department;

import com.example.demo.common.GenericController;
import com.example.demo.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;


//import org.springframework.data.projection.ProjectionFactory;
@RestController
@RequestMapping(DepartmentController.URI)
@CrossOrigin()
public class DepartmentController extends GenericController<Department, Integer> {


    static final String URI = "/api/department";

    //Calling generic controller by passing department repository

    @Autowired
    public DepartmentController(DeptRepo repo) {
        super(URI, repo);

    }

    @RequestMapping
    public ResponseEntity<Page<Department>> all(Pageable pageable, @RequestParam(value = "search", required = false) String search) {
        return super.listResponse(super.allImpl(pageable, search), pageable, search);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    protected ResponseEntity<Department> get(Integer id) {
        return super.respondToGet(id, super.getImpl(id));
    }

    @RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    protected ResponseEntity<Department> create(Department department) throws URISyntaxException {
        if (department.getDeptid() !=  Department.DEFAULT_DEPT_ID) {
            return super.cannotCreateEntityWithKey();
        }
        Department result = super.createImpl(department);
        return super.created(result.getDeptid(),result);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
    protected ResponseEntity<Department> update(Integer id, Department department) {
        return super.getImpl(id)
                .map(oldDepartment -> {
                    Department updatedEntity = super.updateImpl(oldDepartment, department);
                    return super.updated(id, updatedEntity);
                })
                .orElse(super.updateNotFound(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    protected ResponseEntity<Department>  delete(Integer id) {
        super.deleteImpl(id);
        return super.noContent(id);
    }

}


