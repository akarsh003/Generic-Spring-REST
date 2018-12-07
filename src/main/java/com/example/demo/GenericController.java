package com.example.demo;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.*;
import com.example.demo.department.DeptRepo;
import com.example.demo.employee.EmplRepo;
import com.example.demo.employee.EmployeeController;
import com.example.demo.model.Department;
import com.example.demo.model.Employee;
import com.example.demo.*;

////import com.example.demo.model.Department;
//
////import com.google.common.base.Throwables;
//import com.google.common.collect.Lists;
//import com.google.common.collect.Maps;



@RestController
public abstract class GenericController<T, ID extends Serializable> {
//    private final Logger log = LoggerFactory.getLogger(getClass());
    private ApplicationRepository<T, ID> repo;
   private String baseUri;
    public GenericController(String baseUri, ApplicationRepository<T, ID> repo) {
       this.baseUri = baseUri;
       this.repo = repo;
   }
//    protected Logger getLogger() {
//       return log;
//   }
    private Specification<T> resolveSpecificationFromInfixExpr(String searchParameters) {
       CrieteriaParser parser = new CrieteriaParser();
       GenericSpecificationBuilder<T> specBuilder = new GenericSpecificationBuilder<>();
       Function<SpecSearchCriteria, Specification<T>> specificationFactory = getSpecificationObject();
       return specBuilder.build(parser.parse(searchParameters), specificationFactory);
   }
    protected Function<SpecSearchCriteria, Specification<T>> getSpecificationObject() {
       return BaseGenericSpecification::new;
   }
    protected Page<T> allImpl(Pageable pageable, String search) {
       if (search != null && !search.isEmpty()) {
           Specification<T> spec = resolveSpecificationFromInfixExpr(search);
           Page<T> data = repo.findAll(spec, pageable);
           return data;
       } else {
           Page<T> data = repo.findAll(pageable);
           return data;
       }
   }
    protected Optional<T> getImpl(ID id) {
       return this.repo.findById(id);
   }
    
    
    protected T createImpl(T json) {
    	
       T created = this.repo.save(json);
       return created;
       
   }
    
    
    protected T updateImpl(T oldObj, T newObj) {
    	
       BeanUtils.copyProperties(newObj, oldObj);
       T updated = this.repo.save(oldObj);
       return updated;
       
   }
    
    protected boolean deleteImpl(ID id) {
       this.repo.deleteById(id);
       return true;
   }
    protected ResponseEntity<T> respondToGet(ID id, Optional<T> result) {
       return result.map(obj ->
               new ResponseEntity<>(
                       obj,
                       HttpStatus.OK)
       )
               .orElse(
                       new ResponseEntity<>(HttpStatus.NOT_FOUND)
               );
   }
    protected ResponseEntity<T> cannotCreateEntityWithKey() {
       return ResponseEntity.badRequest().header("Failure", "A new entity cannot already have an ID").body(null);
   }
    protected ResponseEntity<T> created(ID id, T result) throws URISyntaxException {
       return ResponseEntity.created(new URI(baseUri + id)).body(result);
   }
    protected ResponseEntity<T> updated(ID id, T result) {
       return new ResponseEntity<>(result, HttpStatus.OK);
   }
    protected ResponseEntity<T> noContent(ID id) {
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }
    protected ResponseEntity<T> updateNotFound(ID id) {
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
   }
    protected ResponseEntity<Page<T>> listResponse(Page<T> allImpl, Pageable pageable, String search) {
       return new ResponseEntity<>(allImpl, HttpStatus.NOT_FOUND);
   }
    
    
    
//    @RequestMapping(value="/{id}", method=RequestMethod.PUT, consumes={MediaType.APPLICATION_JSON_VALUE})
  public @ResponseBody T updateput(ID id, @RequestBody T json) {
     
      T entity = (T) this.repo.findById(id);
      
          BeanUtils.copyProperties(entity, json);
      
      T updated = this.repo.save( entity);

//      Map<String, Object> m = Maps.newHashMap();
//      m.put("success", true);
//      m.put("id", id);
//      m.put("updated", updated);
      return updated;
  }
}