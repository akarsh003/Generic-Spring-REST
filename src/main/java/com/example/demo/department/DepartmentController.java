package com.example.demo.department;

import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.projection.ProjectionFactory;

import com.example.demo.GenericController;
import com.example.demo.employee.EmplRepo;
import com.example.demo.model.Department;

//import net.bytebuddy.dynamic.DynamicType.Builder.FieldDefinition.Optional;

@RestController
//@RequestMapping("/department")
@RequestMapping(DepartmentController.URI)

public class DepartmentController extends GenericController<Department, Integer> {

	@Autowired
	private DeptRepo repository;
	
//	@Autowired
//	public DepartmentController(DeptRepo repo) {
//		super(repo);
//
//	}
	
	static final String URI = "/department";
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
   protected ResponseEntity<Department> get(@PathVariable Integer id) {
       return super.respondToGet(id, super.getImpl(id));
   }
    
    
    @RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
   protected ResponseEntity<Department> create(@RequestBody Department department) throws URISyntaxException {
    	
//       if (department.getDeptid() != null) {
//           return super.cannotCreateEntityWithKey();
//       }
       
       Department result = super.createImpl(department);
       return super.created(result.getDeptid(),result);
    }
    

    
    
    @RequestMapping(value="/{id}", method=RequestMethod.PUT, consumes={MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody Department update(@PathVariable int id, @RequestBody Department json) {

       Department entity = repository.findById(id);

            BeanUtils.copyProperties(entity, json);

            Department result = super.createImpl(entity);

        return result;
    }
    

    
   @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
   protected ResponseEntity<Department>  delete(@PathVariable Integer id) {
       super.deleteImpl(id);
       return super.noContent(id);
   }
}
	

