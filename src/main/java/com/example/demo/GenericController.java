package com.example.demo;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.web.PagedResourcesAssembler;
//import org.springframework.hateoas.MediaTypes;
//import org.springframework.hateoas.PagedResources;
//import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.demo.*;
import com.example.demo.employee.EmplRepo;
import com.example.demo.*;

////import com.example.demo.model.Department;
//
////import com.google.common.base.Throwables;
//import com.google.common.collect.Lists;
//import com.google.common.collect.Maps;


public abstract class GenericController<T, ID extends Serializable> {


    private JpaRepository<T, ID> repo;
    private EmplRepo emprepo;


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
    
    
    @RequestMapping(method=RequestMethod.POST, consumes={MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody T create(@RequestBody T json) {

        T created = this.repo.save(json);

//        Map<String, Object> m = Maps.newHashMap();
//        m.put("success", true);
//        m.put("created", created);
        return created;
    }
    
    
    @RequestMapping(value = "/search/byadvsearch",method=RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> findAllByAdvPredicate(@RequestParam(value = "advsearch") String search,Pageable pageable){
		
        
        Specification<T> spec = resolveSpecificationFromInfixExpr(search);
        Page<T> emplo=emprepo.findAll(spec, pageable);
//        Page<InlineRecordsEmployee> projected = emplo.map(l -> factory.createProjection(InlineRecordsEmployee.class, l));
//        PagedResources<Resource<InlineRecordsEmployee>> resources = assembleremployee.toResource(projected);
        return ResponseEntity.ok(emplo);

       
	}
	
	
    protected Specification<T> resolveSpecificationFromInfixExpr(String searchParameters) {
        CrieteriaParser parser = new CrieteriaParser();
        GenericSpecificationBuilder<T> specBuilder = new GenericSpecificationBuilder<>();
        return specBuilder.build(parser.parse(searchParameters), EmployeeSpecification::new);
    }

   


    
    @RequestMapping(value="/{id}", method=RequestMethod.PUT, consumes={MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody T update(@PathVariable ID id, @RequestBody T json) {
       
        T entity = (T) this.repo.findById(id);
        
            BeanUtils.copyProperties(entity, json);
        
        T updated = this.repo.save( entity);

//        Map<String, Object> m = Maps.newHashMap();
//        m.put("success", true);
//        m.put("id", id);
//        m.put("updated", updated);
        return updated;
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public @ResponseBody String delete(@PathVariable ID id) {
        this.repo.deleteById(id);
//        Map<String, Object> m = Maps.newHashMap();
//        m.put("success", true);
        return "Successs";
    }
}