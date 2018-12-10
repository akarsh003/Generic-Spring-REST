package com.example.demo.employee;

//import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import com.example.demo.common.ApplicationRepository;
//import org.springframework.data.repository.query.Param;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;
//import org.springframework.data.rest.core.annotation.RestResource;
//import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.RequestMapping;

//import com.example.demo.model.Department;
import com.example.demo.model.Employee;


interface EmployeeRepo extends ApplicationRepository<Employee, Integer> {

}
