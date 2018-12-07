package com.example.demo.department;

import java.util.Date;
import java.util.List;
import java.util.Optional;

//import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.query.Param;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;
//import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.ApplicationRepository;
import com.example.demo.model.Department;
import com.example.demo.model.Employee;



@CrossOrigin()
//@RepositoryRestResource(exported=false/*,excerptProjection = InlineRecordsDepartment.class*/)
public interface DeptRepo extends ApplicationRepository<Department, Integer> {

	public Department findById(int id);
	
//	@RestResource()
//	public Page<T> findByid(int id,Pageable pageable);
//	@RestResource()
//	public Page<Department> findAll(Pageable pageable);
//	Page<Department> findAll(Specification spec,Pageable pageable);

//	public int findDeptId();
	
//	Department findByEmployee(int id);
}


