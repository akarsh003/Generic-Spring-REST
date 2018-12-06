package com.example.demo;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
//import org.springframework.data.web.PagedResourcesAssembler;
//import org.springframework.hateoas.MediaTypes;
//import org.springframework.hateoas.PagedResources;
//import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.demo.employee.EmployeeController;
import com.example.demo.model.Department;
//import com.example.demo.department.InlineRecordsDepartment;
//import com.example.demo.employee.InlineRecordsEmployee;
import com.example.demo.model.Employee;
//import com.google.common.collect.Maps;

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
    
    
    @RequestMapping(method=RequestMethod.POST, consumes={MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody T create(@RequestBody T json) {

        T created = this.repo.save(json);

//        Map<String, Object> m = Maps.newHashMap();
//        m.put("success", true);
//        m.put("created", created);
        return created;
    }
    
//    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
//	Optional<?> replace(@PathVariable ID id) {
//
////    	T x=this.get(id);
//    	Optional<T> x= this.repo.findById(id);
//
//    	
//		return this.repo.findById(id)
//			.map(T -> {
//				T.setid(newT.getDeptid());
//				/*Department.setDeptname(newDepartment.getDeptname());
//				Department.setId(newDepartment.getId());*/
//				
//				return this.repo.save(T);
//			});
//			
//	}

   
//    @RequestMapping(value = "/{id}",
//            method = RequestMethod.PUT,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//       
//        public Object update(@PathVariable ID id, @Valid @RequestBody T dto) throws URISyntaxException {
//            return Optional.ofNullable(this.repo.findById(id))
//                .map(obj -> {
//                    T updatedEntity = this.repo.save(mapper.updateDomain(obj, dto));
//                    return new ResponseEntity<>(mapper.fromDomain(updatedEntity), HttpStatus.OK);
//                })
//                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//        }

    
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