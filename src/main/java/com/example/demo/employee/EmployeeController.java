package com.example.demo.employee;

import org.springframework.beans.factory.annotation.Autowired;
import java.net.URISyntaxException;

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

import com.example.demo.GenericController;
import com.example.demo.model.Employee;



@RequestMapping(EmployeeController.URI)
@RestController
//@RequestMapping("/employee")
public class EmployeeController extends GenericController<Employee,Integer>{

//	Calling generic controller by passing employee repository
    static final String URI = "/employee";
    @Autowired
    public EmployeeController(EmplRepo repo) {
        super(URI, repo);
     }
//	@Autowired
//	public EmployeeController(EmplRepo repo) {
//		super(repo);
//	}

    @RequestMapping
    public ResponseEntity<Page<Employee>> all(Pageable pageable, @RequestParam(value = "search", required = false) String search) {
        return super.listResponse(super.allImpl(pageable, search), pageable, search);
    }
    
    
     @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    protected ResponseEntity<Employee> get(@PathVariable Integer id) {
        return super.respondToGet(id, super.getImpl(id));
    }
     
     
     @RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    protected ResponseEntity<Employee> create(@RequestBody Employee employee) throws URISyntaxException {
    	 
        if (employee.getId() != 0) {
            return super.cannotCreateEntityWithKey();
        }
        Employee result = super.createImpl(employee);
        return super.created(result.getId(),result);
     }
     
     
     @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
     protected ResponseEntity<Employee> update(@PathVariable Integer id,@RequestBody Employee employee) {
         return super.getImpl(id)
                 .map(oldEmployee -> {
                     Employee updatedEntity = super.updateImpl(oldEmployee, employee);
                     return super.updated(id, updatedEntity);
                 })
                 .orElse(super.updateNotFound(id));
     }

     @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
     protected ResponseEntity<Employee>  delete(@PathVariable Integer id) {
         super.deleteImpl(id);
         return super.noContent(id);
     }
}
