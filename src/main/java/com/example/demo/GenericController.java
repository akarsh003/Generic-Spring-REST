package com.example.demo;

import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.projection.ProjectionFactory;
//import org.springframework.data.web.PagedResourcesAssembler;
//import org.springframework.hateoas.MediaTypes;
//import org.springframework.hateoas.PagedResources;
//import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.demo.employee.EmployeeController;
//import com.example.demo.department.InlineRecordsDepartment;
//import com.example.demo.employee.InlineRecordsEmployee;
import com.example.demo.model.Employee;

////import com.example.demo.model.Department;
//
////import com.google.common.base.Throwables;
//import com.google.common.collect.Lists;
//import com.google.common.collect.Maps;


public abstract class GenericController<T, ID extends Serializable> {


    private JpaRepository<T, ID> repo;


    public GenericController(JpaRepository<T, ID> repo/*,ProjectionFactory factory/*,PagedResourcesAssembler<InlineRecordsDepartment> assemblerdepartment,PagedResourcesAssembler<InlineRecordsEmployee> assembleremployee*/) {
        this.repo = repo;
    }

    
    @RequestMapping
	public ResponseEntity<?> all(Pageable pageable){
			 
   	
    	 Page<T> emplo=this.repo.findAll(pageable);
	        return ResponseEntity.ok(emplo);
      
		
	}

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public @ResponseBody Optional<T> get(@PathVariable ID id) {
        return this.repo.findById(id);
    }
    
    
//    @RequestMapping(method=RequestMethod.POST, consumes={MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody Map<String, Object> create(@RequestBody T json) {
//
//        T created = this.repo.save(json);
//
//        Map<String, Object> m = Maps.newHashMap();
//        m.put("success", true);
//        m.put("created", created);
//        return m;
//    }
//   
//    
//    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
//    public @ResponseBody Map<String, Object> delete(@PathVariable ID id) {
//        this.repo.deleteById(id);
//        Map<String, Object> m = Maps.newHashMap();
//        m.put("success", true);
//        return m;
//    }
}