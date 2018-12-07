package com.example.demo;

import java.net.URI;
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
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.function.Function;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public  class BaseGenericSpecification<T> implements Specification<T> {
    private SpecSearchCriteria criteria;
    BaseGenericSpecification(final SpecSearchCriteria criteria) {
       super();
       this.criteria = criteria;
   }
    public SpecSearchCriteria getCriteria() {
       return criteria;
   }
    @Override
   public Predicate toPredicate(final Root<T> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {
       switch (criteria.getOperation()) {
           case EQUALITY:
               return builder.equal(root.get(criteria.getKey()), criteria.getValue());
           case NEGATION:
               return builder.notEqual(root.get(criteria.getKey()), criteria.getValue());
           case GREATER_THAN:
               return builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString());
           case LESS_THAN:
               return builder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString());
           case LIKE:
               return builder.like(root.get(criteria.getKey()), criteria.getValue().toString());
           case STARTS_WITH:
               return builder.like(root.get(criteria.getKey()), criteria.getValue() + "%");
           case ENDS_WITH:
               return builder.like(root.get(criteria.getKey()), "%" + criteria.getValue());
           case CONTAINS:
               return builder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
           default:
               return null;
       }
   }
} 
