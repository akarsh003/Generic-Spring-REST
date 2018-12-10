package com.example.demo.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.function.Function;

//import org.springframework.data.web.PagedResourcesAssembler;
//import org.springframework.hateoas.MediaTypes;
//import org.springframework.hateoas.PagedResources;
//import org.springframework.hateoas.Resource;
//import com.example.demo.department.InlineRecordsDepartment;
//import com.example.demo.employee.InlineRecordsEmployee;


public abstract class GenericController<T, ID extends Serializable> {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private ApplicationRepository<T, ID> repo;
    private String baseUri;

    public GenericController(String baseUri, ApplicationRepository<T, ID> repo) {
        this.baseUri = baseUri;
        this.repo = repo;
    }

    protected Logger getLogger() {
        return log;
    }

    private Specification<T> resolveSpecificationFromInfixExpr(String searchParameters) {
        CriteriaParser parser = new CriteriaParser();
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
        BeanUtils.copyProperties(oldObj, newObj);
        T updated = this.repo.save(oldObj);
        return updated;
    }


    protected boolean deleteImpl(ID id) {
        this.repo.deleteById(id);
        return true;
    }

    protected ResponseEntity<T> respondToGet(ID id, Optional<T> result) {
        log.debug("REST get request on {} returned {}", baseUri + "/" + id.toString(), result);
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
        log.debug("REST Created request on {} returned {} with id {}", baseUri, result, id);
        return ResponseEntity.created(new URI(baseUri + id)).body(result);
    }

    protected ResponseEntity<T> updated(ID id, T result) {
        log.debug("REST Update request on {} returned {}", baseUri + "/" + id.toString(), result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    protected ResponseEntity<T> noContent(ID id) {
        log.debug("REST delete request on {} success", baseUri + "/" + id.toString());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    protected ResponseEntity<T> updateNotFound(ID id) {
        log.debug("REST update request on {} returned not found", baseUri + "/" + id.toString());
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    protected < ResultType > ResponseEntity<Page<ResultType>> listResponse(Page<ResultType> allImpl, Pageable pageable, String search) {
        log.debug("REST List request on {} with pageable params {} and search {} ", baseUri , pageable, search);
        return new ResponseEntity<>(allImpl, HttpStatus.OK);
    }
}